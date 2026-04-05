package com.university.exam.repository;

import com.university.exam.entity.Invigilator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvigilatorRepository extends JpaRepository<Invigilator, Long> {
    Optional<Invigilator> findByEmployeeId(String employeeId);
    List<Invigilator> findByIsAvailable(Boolean isAvailable);
    boolean existsByEmployeeId(String employeeId);
    
    @Query("SELECT i FROM Invigilator i WHERE i.isAvailable = true ORDER BY i.id")
    List<Invigilator> findAvailableInvigilators();
}
