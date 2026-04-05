package com.university.exam.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "invigilator_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvigilatorAssignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invigilator_id", nullable = false)
    private Invigilator invigilator;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvigilatorRole role = InvigilatorRole.ASSISTANT;
    
    @Column(name = "assignment_count")
    private Integer assignmentCount = 1;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum InvigilatorRole {
        CHIEF, ASSISTANT, RELIEF
    }
}
