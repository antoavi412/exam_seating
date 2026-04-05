package com.university.exam.repository;

import com.university.exam.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findByHallCode(String hallCode);
    List<Hall> findByIsActive(Boolean isActive);
    boolean existsByHallCode(String hallCode);
}
