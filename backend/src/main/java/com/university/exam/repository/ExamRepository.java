package com.university.exam.repository;

import com.university.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByExamCode(String examCode);
    List<Exam> findByExamDate(LocalDate examDate);
    List<Exam> findByStatus(Exam.ExamStatus status);
    boolean existsByExamCode(String examCode);
    
    @Query("SELECT e FROM Exam e LEFT JOIN FETCH e.students WHERE e.id = :examId")
    Optional<Exam> findByIdWithStudents(Long examId);
}
