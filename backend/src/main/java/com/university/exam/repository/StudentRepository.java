package com.university.exam.repository;

import com.university.exam.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    List<Student> findByDepartmentId(Long departmentId);
    List<Student> findByYear(Integer year);
    boolean existsByRollNumber(String rollNumber);
    
    @Query("SELECT s FROM Student s JOIN FETCH s.department")
    List<Student> findAllWithDepartment();
}
