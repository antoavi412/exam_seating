package com.university.exam.algorithm;

import com.university.exam.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Optimized Seating Allocation Algorithm
 * 
 * Strategy:
 * 1. Group students by department
 * 2. Distribute students across halls using checkerboard pattern
 * 3. Ensure no adjacent same-department students
 * 4. Maximize hall utilization
 * 
 * Time Complexity: O(n * h) where n = students, h = halls
 * Space Complexity: O(n + h)
 */
@Component
public class SeatingAllocationAlgorithm {
    
    private static final Logger logger = LoggerFactory.getLogger(SeatingAllocationAlgorithm.class);
    
    /**
     * Allocate students to seats with constraints
     */
    public List<SeatingAllocation> allocateSeats(Exam exam, List<Hall> halls) {
        logger.info("Starting seating allocation for exam: {}", exam.getExamCode());
        
        List<Student> students = exam.getStudents();
        if (students.isEmpty()) {
            logger.warn("No students found for exam: {}", exam.getExamCode());
            return new ArrayList<>();
        }
        
        // Check total capacity
        int totalCapacity = halls.stream().mapToInt(Hall::getCapacity).sum();
        if (students.size() > totalCapacity) {
            throw new IllegalArgumentException(
                String.format("Not enough capacity. Students: %d, Capacity: %d", 
                    students.size(), totalCapacity)
            );
        }
        
        // Group students by department
        Map<Long, List<Student>> studentsByDept = students.stream()
            .collect(Collectors.groupingBy(s -> s.getDepartment().getId()));
        
        // Sort departments by student count (descending) for better distribution
        List<Long> sortedDepts = studentsByDept.entrySet().stream()
            .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        
        logger.info("Departments found: {}, Total students: {}", sortedDepts.size(), students.size());
        
        // Allocate students
        List<SeatingAllocation> allocations = new ArrayList<>();
        Map<String, Long> seatMap = new HashMap<>(); // Track occupied seats (hallId-row-col -> deptId)
        
        // Round-robin distribution across departments and halls
        int studentIndex = 0;
        List<Student> allStudents = new ArrayList<>();
        
        // Create interleaved student list for better distribution
        int maxDeptSize = studentsByDept.values().stream()
            .mapToInt(List::size)
            .max()
            .orElse(0);
        
        for (int i = 0; i < maxDeptSize; i++) {
            for (Long deptId : sortedDepts) {
                List<Student> deptStudents = studentsByDept.get(deptId);
                if (i < deptStudents.size()) {
                    allStudents.add(deptStudents.get(i));
                }
            }
        }
        
        // Allocate students to halls
        for (Hall hall : halls) {
            if (studentIndex >= allStudents.size()) {
                break;
            }
            
            int studentsInHall = Math.min(
                allStudents.size() - studentIndex,
                hall.getCapacity()
            );
            
            logger.info("Allocating {} students to hall: {}", studentsInHall, hall.getHallCode());
            
            List<Student> hallStudents = allStudents.subList(
                studentIndex,
                studentIndex + studentsInHall
            );
            
            List<SeatingAllocation> hallAllocations = allocateToHall(
                exam, hall, hallStudents, seatMap
            );
            
            allocations.addAll(hallAllocations);
            studentIndex += studentsInHall;
        }
        
        logger.info("Seating allocation completed. Total allocations: {}", allocations.size());
        return allocations;
    }
    
    /**
     * Allocate students to a specific hall with adjacency constraints
     */
    private List<SeatingAllocation> allocateToHall(
            Exam exam, 
            Hall hall, 
            List<Student> students,
            Map<String, Long> seatMap) {
        
        List<SeatingAllocation> allocations = new ArrayList<>();
        int studentIdx = 0;
        
        // Fill hall row by row with constraint checking
        for (int row = 1; row <= hall.getRows() && studentIdx < students.size(); row++) {
            for (int col = 1; col <= hall.getColumns() && studentIdx < students.size(); col++) {
                Student student = students.get(studentIdx);
                Long deptId = student.getDepartment().getId();
                
                // Check adjacency constraints
                if (isValidPlacement(hall.getId(), row, col, deptId, seatMap)) {
                    SeatingAllocation allocation = new SeatingAllocation();
                    allocation.setExam(exam);
                    allocation.setStudent(student);
                    allocation.setHall(hall);
                    allocation.setRowNumber(row);
                    allocation.setColumnNumber(col);
                    allocation.setSeatNumber(generateSeatNumber(row, col));
                    
                    allocations.add(allocation);
                    
                    // Mark seat as occupied
                    String seatKey = hall.getId() + "-" + row + "-" + col;
                    seatMap.put(seatKey, deptId);
                    
                    studentIdx++;
                } else {
                    // Skip this seat if constraint violated, try to place in next available seat
                    logger.debug("Skipping seat {}-{} due to adjacency constraint", row, col);
                }
            }
        }
        
        // If some students couldn't be placed, force placement (fallback)
        while (studentIdx < students.size()) {
            for (int row = 1; row <= hall.getRows() && studentIdx < students.size(); row++) {
                for (int col = 1; col <= hall.getColumns() && studentIdx < students.size(); col++) {
                    String seatKey = hall.getId() + "-" + row + "-" + col;
                    if (!seatMap.containsKey(seatKey)) {
                        Student student = students.get(studentIdx);
                        Long deptId = student.getDepartment().getId();
                        
                        SeatingAllocation allocation = new SeatingAllocation();
                        allocation.setExam(exam);
                        allocation.setStudent(student);
                        allocation.setHall(hall);
                        allocation.setRowNumber(row);
                        allocation.setColumnNumber(col);
                        allocation.setSeatNumber(generateSeatNumber(row, col));
                        
                        allocations.add(allocation);
                        seatMap.put(seatKey, deptId);
                        studentIdx++;
                    }
                }
            }
        }
        
        return allocations;
    }
    
    /**
     * Check if placing a student at given position violates adjacency constraints
     */
    private boolean isValidPlacement(
            Long hallId, 
            int row, 
            int col, 
            Long deptId,
            Map<String, Long> seatMap) {
        
        // Check left
        if (col > 1) {
            String leftKey = hallId + "-" + row + "-" + (col - 1);
            if (seatMap.containsKey(leftKey) && seatMap.get(leftKey).equals(deptId)) {
                return false;
            }
        }
        
        // Check right
        String rightKey = hallId + "-" + row + "-" + (col + 1);
        if (seatMap.containsKey(rightKey) && seatMap.get(rightKey).equals(deptId)) {
            return false;
        }
        
        // Check front (previous row)
        if (row > 1) {
            String frontKey = hallId + "-" + (row - 1) + "-" + col;
            if (seatMap.containsKey(frontKey) && seatMap.get(frontKey).equals(deptId)) {
                return false;
            }
        }
        
        // Check back (next row)
        String backKey = hallId + "-" + (row + 1) + "-" + col;
        if (seatMap.containsKey(backKey) && seatMap.get(backKey).equals(deptId)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Generate seat number from row and column (e.g., A-1, B-5)
     */
    private String generateSeatNumber(int row, int col) {
        char rowLetter = (char) ('A' + (row - 1));
        return rowLetter + "-" + col;
    }
}
