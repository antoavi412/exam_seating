package com.university.exam.service;

import com.university.exam.algorithm.InvigilatorAllocationAlgorithm;
import com.university.exam.algorithm.SeatingAllocationAlgorithm;
import com.university.exam.dto.ExamDTO;
import com.university.exam.dto.SeatingAllocationDTO;
import com.university.exam.entity.*;
import com.university.exam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamService {
    
    @Autowired
    private ExamRepository examRepository;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private HallRepository hallRepository;
    
    @Autowired
    private InvigilatorRepository invigilatorRepository;
    
    @Autowired
    private SeatingAllocationRepository seatingAllocationRepository;
    
    @Autowired
    private InvigilatorAssignmentRepository invigilatorAssignmentRepository;
    
    @Autowired
    private SeatingAllocationAlgorithm seatingAlgorithm;
    
    @Autowired
    private InvigilatorAllocationAlgorithm invigilatorAlgorithm;
    
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public ExamDTO getExamById(Long id) {
        Exam exam = examRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
        return convertToDTO(exam);
    }
    
    public ExamDTO createExam(ExamDTO examDTO) {
        if (examRepository.existsByExamCode(examDTO.getExamCode())) {
            throw new RuntimeException("Exam with code " + examDTO.getExamCode() + " already exists");
        }
        
        Exam exam = new Exam();
        exam.setExamCode(examDTO.getExamCode());
        exam.setSubject(examDTO.getSubject());
        exam.setExamDate(examDTO.getExamDate());
        exam.setStartTime(examDTO.getStartTime());
        exam.setEndTime(examDTO.getEndTime());
        exam.setDuration(examDTO.getDuration());
        exam.setStatus(Exam.ExamStatus.SCHEDULED);
        
        // Add students to exam
        if (examDTO.getStudentIds() != null && !examDTO.getStudentIds().isEmpty()) {
            List<Student> students = studentRepository.findAllById(examDTO.getStudentIds());
            exam.setStudents(students);
            exam.setTotalStudents(students.size());
        }
        
        Exam saved = examRepository.save(exam);
        return convertToDTO(saved);
    }
    
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        Exam exam = examRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
        
        exam.setSubject(examDTO.getSubject());
        exam.setExamDate(examDTO.getExamDate());
        exam.setStartTime(examDTO.getStartTime());
        exam.setEndTime(examDTO.getEndTime());
        exam.setDuration(examDTO.getDuration());
        
        if (examDTO.getStudentIds() != null) {
            List<Student> students = studentRepository.findAllById(examDTO.getStudentIds());
            exam.setStudents(students);
            exam.setTotalStudents(students.size());
        }
        
        Exam updated = examRepository.save(exam);
        return convertToDTO(updated);
    }
    
    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }
    
    public List<SeatingAllocationDTO> allocateSeating(Long examId) {
        Exam exam = examRepository.findByIdWithStudents(examId)
            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
        
        if (exam.getStudents().isEmpty()) {
            throw new RuntimeException("No students registered for this exam");
        }
        
        // Get active halls
        List<Hall> halls = hallRepository.findByIsActive(true);
        if (halls.isEmpty()) {
            throw new RuntimeException("No active halls available");
        }
        
        // Delete existing allocations
        seatingAllocationRepository.deleteByExamId(examId);
        
        // Allocate seats
        List<SeatingAllocation> allocations = seatingAlgorithm.allocateSeats(exam, halls);
        
        // Save allocations
        List<SeatingAllocation> saved = seatingAllocationRepository.saveAll(allocations);
        
        return saved.stream()
            .map(this::convertSeatingToDTO)
            .collect(Collectors.toList());
    }
    
    public List<InvigilatorAssignment> allocateInvigilators(Long examId) {
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new RuntimeException("Exam not found with id: " + examId));
        
        // Get halls used in this exam
        List<SeatingAllocation> seatingAllocations = seatingAllocationRepository.findByExamId(examId);
        if (seatingAllocations.isEmpty()) {
            throw new RuntimeException("No seating allocation found. Please allocate seats first.");
        }
        
        List<Hall> usedHalls = seatingAllocations.stream()
            .map(SeatingAllocation::getHall)
            .distinct()
            .collect(Collectors.toList());
        
        // Get available invigilators
        List<Invigilator> invigilators = invigilatorRepository.findAvailableInvigilators();
        if (invigilators.isEmpty()) {
            throw new RuntimeException("No invigilators available");
        }
        
        // Delete existing assignments
        invigilatorAssignmentRepository.deleteByExamId(examId);
        
        // Assign invigilators
        List<InvigilatorAssignment> assignments = invigilatorAlgorithm.assignInvigilators(
            exam, usedHalls, invigilators
        );
        
        // Save assignments
        return invigilatorAssignmentRepository.saveAll(assignments);
    }
    
    public List<SeatingAllocationDTO> getSeatingAllocation(Long examId) {
        return seatingAllocationRepository.findByExamIdWithDetails(examId).stream()
            .map(this::convertSeatingToDTO)
            .collect(Collectors.toList());
    }
    
    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setExamCode(exam.getExamCode());
        dto.setSubject(exam.getSubject());
        dto.setExamDate(exam.getExamDate());
        dto.setStartTime(exam.getStartTime());
        dto.setEndTime(exam.getEndTime());
        dto.setDuration(exam.getDuration());
        dto.setTotalStudents(exam.getTotalStudents());
        dto.setStatus(exam.getStatus().name());
        
        if (exam.getStudents() != null) {
            dto.setStudentIds(exam.getStudents().stream()
                .map(Student::getId)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    private SeatingAllocationDTO convertSeatingToDTO(SeatingAllocation allocation) {
        SeatingAllocationDTO dto = new SeatingAllocationDTO();
        dto.setId(allocation.getId());
        dto.setExamId(allocation.getExam().getId());
        dto.setStudentId(allocation.getStudent().getId());
        dto.setStudentName(allocation.getStudent().getName());
        dto.setStudentRollNumber(allocation.getStudent().getRollNumber());
        dto.setDepartmentCode(allocation.getStudent().getDepartment().getCode());
        dto.setHallId(allocation.getHall().getId());
        dto.setHallCode(allocation.getHall().getHallCode());
        dto.setHallName(allocation.getHall().getName());
        dto.setRowNumber(allocation.getRowNumber());
        dto.setColumnNumber(allocation.getColumnNumber());
        dto.setSeatNumber(allocation.getSeatNumber());
        return dto;
    }
}
