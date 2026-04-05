package com.university.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatingAllocationDTO {
    private Long id;
    private Long examId;
    private Long studentId;
    private String studentName;
    private String studentRollNumber;
    private String departmentCode;
    private Long hallId;
    private String hallCode;
    private String hallName;
    private Integer rowNumber;
    private Integer columnNumber;
    private String seatNumber;
}
