package com.university.exam.controller;

import com.university.exam.dto.ExamDTO;
import com.university.exam.dto.SeatingAllocationDTO;
import com.university.exam.entity.InvigilatorAssignment;
import com.university.exam.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
@CrossOrigin(origins = "*")
public class ExamController {
    
    @Autowired
    private ExamService examService;
    
    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        return ResponseEntity.ok(examService.getAllExams());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getExamById(id));
    }
    
    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody ExamDTO examDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(examService.createExam(examDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(
            @PathVariable Long id, 
            @Valid @RequestBody ExamDTO examDTO) {
        return ResponseEntity.ok(examService.updateExam(id, examDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/allocate-seating")
    public ResponseEntity<List<SeatingAllocationDTO>> allocateSeating(@PathVariable Long id) {
        return ResponseEntity.ok(examService.allocateSeating(id));
    }
    
    @PostMapping("/{id}/allocate-invigilators")
    public ResponseEntity<List<InvigilatorAssignment>> allocateInvigilators(@PathVariable Long id) {
        return ResponseEntity.ok(examService.allocateInvigilators(id));
    }
    
    @GetMapping("/{id}/seating")
    public ResponseEntity<List<SeatingAllocationDTO>> getSeatingAllocation(@PathVariable Long id) {
        return ResponseEntity.ok(examService.getSeatingAllocation(id));
    }
}
