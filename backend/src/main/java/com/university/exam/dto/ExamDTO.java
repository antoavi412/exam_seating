package com.university.exam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Long id;
    
    @NotBlank(message = "Exam code is required")
    private String examCode;
    
    @NotBlank(message = "Subject is required")
    private String subject;
    
    @NotNull(message = "Exam date is required")
    private LocalDate examDate;
    
    @NotNull(message = "Start time is required")
    private LocalTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalTime endTime;
    
    @NotNull(message = "Duration is required")
    private Integer duration;
    
    private Integer totalStudents;
    private String status;
    private List<Long> studentIds = new ArrayList<>();
}
