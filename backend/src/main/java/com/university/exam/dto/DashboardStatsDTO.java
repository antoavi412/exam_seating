package com.university.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private Long totalStudents;
    private Long totalHalls;
    private Long totalInvigilators;
    private Long totalExams;
    private Long scheduledExams;
    private Long completedExams;
    private Integer totalHallCapacity;
}
