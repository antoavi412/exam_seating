package com.university.exam.service;

import com.university.exam.dto.DashboardStatsDTO;
import com.university.exam.entity.Exam;
import com.university.exam.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private HallRepository hallRepository;
    
    @Autowired
    private InvigilatorRepository invigilatorRepository;
    
    @Autowired
    private ExamRepository examRepository;
    
    public DashboardStatsDTO getDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        stats.setTotalStudents(studentRepository.count());
        stats.setTotalHalls(hallRepository.count());
        stats.setTotalInvigilators(invigilatorRepository.count());
        stats.setTotalExams(examRepository.count());
        
        stats.setScheduledExams(
            examRepository.findByStatus(Exam.ExamStatus.SCHEDULED).stream().count()
        );
        
        stats.setCompletedExams(
            examRepository.findByStatus(Exam.ExamStatus.COMPLETED).stream().count()
        );
        
        stats.setTotalHallCapacity(
            hallRepository.findAll().stream()
                .mapToInt(hall -> hall.getCapacity())
                .sum()
        );
        
        return stats;
    }
}
