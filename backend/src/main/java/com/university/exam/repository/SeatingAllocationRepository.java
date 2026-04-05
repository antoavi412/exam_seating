package com.university.exam.repository;

import com.university.exam.entity.SeatingAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatingAllocationRepository extends JpaRepository<SeatingAllocation, Long> {
    List<SeatingAllocation> findByExamId(Long examId);
    List<SeatingAllocation> findByHallId(Long hallId);
    List<SeatingAllocation> findByExamIdAndHallId(Long examId, Long hallId);
    
    @Query("SELECT sa FROM SeatingAllocation sa " +
           "JOIN FETCH sa.student s " +
           "JOIN FETCH s.department " +
           "JOIN FETCH sa.hall " +
           "WHERE sa.exam.id = :examId")
    List<SeatingAllocation> findByExamIdWithDetails(@Param("examId") Long examId);
    
    void deleteByExamId(Long examId);
}
