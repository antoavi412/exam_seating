package com.university.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvigilatorDTO {
    private Long id;
    private String employeeId;
    private String name;
    private Long departmentId;
    private String departmentCode;
    private String email;
    private String phone;
    private Boolean isAvailable;
}
