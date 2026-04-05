package com.university.exam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    
    @NotBlank(message = "Roll number is required")
    private String rollNumber;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Department ID is required")
    private Long departmentId;
    
    private String departmentCode;
    private String departmentName;
    
    @NotNull(message = "Year is required")
    private Integer year;
    
    private String email;
}
