package com.university.exam.repository;

import com.university.exam.entity.InvigilatorAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvigilatorAssignmentRepository extends JpaRepository<InvigilatorAssignment, Long> {
    List<InvigilatorAssignment> findByExamId(Long examId);
    List<InvigilatorAssignment> findByInvigilatorId(Long invigilatorId);
    
    @Query("SELECT COUNT(ia) FROM InvigilatorAssignment ia WHERE ia.invigilator.id = :invigilatorId")
    Long countAssignmentsByInvigilatorId(@Param("invigilatorId") Long invigilatorId);
    
    @Query("SELECT ia FROM InvigilatorAssignment ia " +
           "JOIN FETCH ia.invigilator " +
           "JOIN FETCH ia.hall " +
           "WHERE ia.exam.id = :examId")
    List<InvigilatorAssignment> findByExamIdWithDetails(@Param("examId") Long examId);
    
    void deleteByExamId(Long examId);
}
