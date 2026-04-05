package com.university.exam.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {
    private Long id;
    
    @NotBlank(message = "Hall code is required")
    private String hallCode;
    
    @NotBlank(message = "Hall name is required")
    private String name;
    
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;
    
    @NotNull(message = "Rows is required")
    @Min(value = 1, message = "Rows must be at least 1")
    private Integer rows;
    
    @NotNull(message = "Columns is required")
    @Min(value = 1, message = "Columns must be at least 1")
    private Integer columns;
    
    private String building;
    private Integer floor;
    private Boolean isActive;
}
