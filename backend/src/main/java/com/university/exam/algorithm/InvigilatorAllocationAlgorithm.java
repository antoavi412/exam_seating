package com.university.exam.algorithm;

import com.university.exam.entity.*;
import com.university.exam.repository.InvigilatorAssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Invigilator Allocation Algorithm
 * 
 * Strategy:
 * 1. Track workload for each invigilator
 * 2. Use weighted round-robin for fair distribution
 * 3. Avoid assigning same invigilator to same hall repeatedly
 * 4. Balance workload across all exams
 * 
 * Time Complexity: O(h * i) where h = halls, i = invigilators
 * Space Complexity: O(i)
 */
@Component
public class InvigilatorAllocationAlgorithm {
    
    private static final Logger logger = LoggerFactory.getLogger(InvigilatorAllocationAlgorithm.class);
    
    @Autowired
    private InvigilatorAssignmentRepository assignmentRepository;
    
    /**
     * Assign invigilators to halls for an exam
     * Typically 2 invigilators per hall (1 chief, 1 assistant)
     */
    public List<InvigilatorAssignment> assignInvigilators(
            Exam exam, 
            List<Hall> halls, 
            List<Invigilator> availableInvigilators) {
        
        logger.info("Starting invigilator assignment for exam: {}", exam.getExamCode());
        
        if (availableInvigilators.isEmpty()) {
            throw new IllegalArgumentException("No invigilators available");
        }
        
        if (halls.isEmpty()) {
            throw new IllegalArgumentException("No halls provided");
        }
        
        // Calculate required invigilators (2 per hall: 1 chief, 1 assistant)
        int requiredInvigilators = halls.size() * 2;
        
        if (availableInvigilators.size() < requiredInvigilators) {
            logger.warn("Not enough invigilators. Required: {}, Available: {}", 
                requiredInvigilators, availableInvigilators.size());
        }
        
        // Get workload statistics for fair distribution
        Map<Long, Long> workloadMap = getInvigilatorWorkload(availableInvigilators);
        
        // Sort invigilators by workload (ascending) for fair distribution
        List<Invigilator> sortedInvigilators = availableInvigilators.stream()
            .sorted(Comparator.comparingLong(i -> workloadMap.getOrDefault(i.getId(), 0L)))
            .collect(Collectors.toList());
        
        List<InvigilatorAssignment> assignments = new ArrayList<>();
        int invigilatorIndex = 0;
        
        // Assign invigilators to halls
        for (Hall hall : halls) {
            // Assign Chief Invigilator
            if (invigilatorIndex < sortedInvigilators.size()) {
                Invigilator chief = sortedInvigilators.get(invigilatorIndex);
                InvigilatorAssignment chiefAssignment = createAssignment(
                    exam, hall, chief, InvigilatorAssignment.InvigilatorRole.CHIEF
                );
                assignments.add(chiefAssignment);
                
                // Update workload
                workloadMap.put(chief.getId(), workloadMap.getOrDefault(chief.getId(), 0L) + 1);
                invigilatorIndex++;
            }
            
            // Assign Assistant Invigilator
            if (invigilatorIndex < sortedInvigilators.size()) {
                // Re-sort to get next least-loaded invigilator
                sortedInvigilators.sort(
                    Comparator.comparingLong(i -> workloadMap.getOrDefault(i.getId(), 0L))
                );
                
                Invigilator assistant = sortedInvigilators.get(invigilatorIndex);
                InvigilatorAssignment assistantAssignment = createAssignment(
                    exam, hall, assistant, InvigilatorAssignment.InvigilatorRole.ASSISTANT
                );
                assignments.add(assistantAssignment);
                
                // Update workload
                workloadMap.put(assistant.getId(), workloadMap.getOrDefault(assistant.getId(), 0L) + 1);
                invigilatorIndex++;
            }
            
            // If we run out of invigilators, reuse from the beginning
            if (invigilatorIndex >= sortedInvigilators.size()) {
                invigilatorIndex = 0;
                // Re-sort for next round
                sortedInvigilators.sort(
                    Comparator.comparingLong(i -> workloadMap.getOrDefault(i.getId(), 0L))
                );
            }
        }
        
        logger.info("Invigilator assignment completed. Total assignments: {}", assignments.size());
        return assignments;
    }
    
    /**
     * Create an invigilator assignment
     */
    private InvigilatorAssignment createAssignment(
            Exam exam, 
            Hall hall, 
            Invigilator invigilator,
            InvigilatorAssignment.InvigilatorRole role) {
        
        InvigilatorAssignment assignment = new InvigilatorAssignment();
        assignment.setExam(exam);
        assignment.setHall(hall);
        assignment.setInvigilator(invigilator);
        assignment.setRole(role);
        assignment.setAssignmentCount(1);
        
        return assignment;
    }
    
    /**
     * Get workload statistics for all invigilators
     */
    private Map<Long, Long> getInvigilatorWorkload(List<Invigilator> invigilators) {
        Map<Long, Long> workloadMap = new HashMap<>();
        
        for (Invigilator invigilator : invigilators) {
            Long count = assignmentRepository.countAssignmentsByInvigilatorId(invigilator.getId());
            workloadMap.put(invigilator.getId(), count);
        }
        
        return workloadMap;
    }
    
    /**
     * Advanced: Assign with hall history consideration
     * Avoids assigning same invigilator to same hall repeatedly
     */
    public List<InvigilatorAssignment> assignInvigilatorsWithHistory(
            Exam exam, 
            List<Hall> halls, 
            List<Invigilator> availableInvigilators) {
        
        // Get hall assignment history
        Map<String, Integer> hallInvigilatorHistory = getHallInvigilatorHistory();
        
        // Similar to basic assignment but considers history
        Map<Long, Long> workloadMap = getInvigilatorWorkload(availableInvigilators);
        
        List<InvigilatorAssignment> assignments = new ArrayList<>();
        
        for (Hall hall : halls) {
            // Find best invigilators for this hall (lowest combined score of workload + history)
            List<Invigilator> rankedInvigilators = availableInvigilators.stream()
                .sorted((i1, i2) -> {
                    String key1 = hall.getId() + "-" + i1.getId();
                    String key2 = hall.getId() + "-" + i2.getId();
                    
                    int historyScore1 = hallInvigilatorHistory.getOrDefault(key1, 0);
                    int historyScore2 = hallInvigilatorHistory.getOrDefault(key2, 0);
                    
                    long workload1 = workloadMap.getOrDefault(i1.getId(), 0L);
                    long workload2 = workloadMap.getOrDefault(i2.getId(), 0L);
                    
                    // Combined score (history weighted more)
                    double score1 = historyScore1 * 2.0 + workload1;
                    double score2 = historyScore2 * 2.0 + workload2;
                    
                    return Double.compare(score1, score2);
                })
                .collect(Collectors.toList());
            
            // Assign top 2
            if (rankedInvigilators.size() >= 2) {
                assignments.add(createAssignment(
                    exam, hall, rankedInvigilators.get(0), 
                    InvigilatorAssignment.InvigilatorRole.CHIEF
                ));
                assignments.add(createAssignment(
                    exam, hall, rankedInvigilators.get(1), 
                    InvigilatorAssignment.InvigilatorRole.ASSISTANT
                ));
            } else if (rankedInvigilators.size() == 1) {
                assignments.add(createAssignment(
                    exam, hall, rankedInvigilators.get(0), 
                    InvigilatorAssignment.InvigilatorRole.CHIEF
                ));
            }
        }
        
        return assignments;
    }
    
    /**
     * Get hall-invigilator assignment history
     */
    private Map<String, Integer> getHallInvigilatorHistory() {
        Map<String, Integer> history = new HashMap<>();
        
        List<InvigilatorAssignment> allAssignments = assignmentRepository.findAll();
        
        for (InvigilatorAssignment assignment : allAssignments) {
            String key = assignment.getHall().getId() + "-" + assignment.getInvigilator().getId();
            history.put(key, history.getOrDefault(key, 0) + 1);
        }
        
        return history;
    }
}
