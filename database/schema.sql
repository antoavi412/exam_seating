-- ===============================================
-- Exam Seating Arrangement System - Database Schema
-- ===============================================

DROP DATABASE IF EXISTS exam_seating_db;
CREATE DATABASE exam_seating_db;
USE exam_seating_db;

-- ===============================================
-- Table: departments
-- ===============================================
CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_dept_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: students
-- ===============================================
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    department_id BIGINT NOT NULL,
    year INT NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE RESTRICT,
    INDEX idx_student_dept (department_id),
    INDEX idx_student_year (year),
    INDEX idx_student_roll (roll_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: halls
-- ===============================================
CREATE TABLE halls (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    hall_code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    rows INT NOT NULL,
    columns INT NOT NULL,
    building VARCHAR(100),
    floor INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_hall_code (hall_code),
    INDEX idx_hall_active (is_active),
    CHECK (capacity = rows * columns)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: invigilators
-- ===============================================
CREATE TABLE invigilators (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    department_id BIGINT,
    email VARCHAR(100),
    phone VARCHAR(20),
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE SET NULL,
    INDEX idx_invig_emp (employee_id),
    INDEX idx_invig_available (is_available)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: exams
-- ===============================================
CREATE TABLE exams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_code VARCHAR(20) NOT NULL UNIQUE,
    subject VARCHAR(100) NOT NULL,
    exam_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    duration INT NOT NULL COMMENT 'Duration in minutes',
    total_students INT DEFAULT 0,
    status ENUM('SCHEDULED', 'ONGOING', 'COMPLETED', 'CANCELLED') DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_exam_code (exam_code),
    INDEX idx_exam_date (exam_date),
    INDEX idx_exam_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: exam_students (Many-to-Many)
-- ===============================================
CREATE TABLE exam_students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    UNIQUE KEY unique_exam_student (exam_id, student_id),
    INDEX idx_exam (exam_id),
    INDEX idx_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: seating_allocations
-- ===============================================
CREATE TABLE seating_allocations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    hall_id BIGINT NOT NULL,
    row_number INT NOT NULL,
    column_number INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL COMMENT 'Generated seat number like A-1, B-5',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE RESTRICT,
    UNIQUE KEY unique_exam_student_seat (exam_id, student_id),
    UNIQUE KEY unique_exam_hall_seat (exam_id, hall_id, row_number, column_number),
    INDEX idx_seating_exam (exam_id),
    INDEX idx_seating_hall (hall_id),
    INDEX idx_seating_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: invigilator_assignments
-- ===============================================
CREATE TABLE invigilator_assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    invigilator_id BIGINT NOT NULL,
    hall_id BIGINT NOT NULL,
    role ENUM('CHIEF', 'ASSISTANT', 'RELIEF') DEFAULT 'ASSISTANT',
    assignment_count INT DEFAULT 1 COMMENT 'Track total assignments for this invigilator',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE,
    FOREIGN KEY (invigilator_id) REFERENCES invigilators(id) ON DELETE CASCADE,
    FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE RESTRICT,
    UNIQUE KEY unique_exam_invig_hall (exam_id, invigilator_id, hall_id),
    INDEX idx_assign_exam (exam_id),
    INDEX idx_assign_invig (invigilator_id),
    INDEX idx_assign_hall (hall_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: invigilator_workload (for tracking)
-- ===============================================
CREATE TABLE invigilator_workload (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invigilator_id BIGINT NOT NULL,
    total_assignments INT DEFAULT 0,
    last_assigned_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (invigilator_id) REFERENCES invigilators(id) ON DELETE CASCADE,
    UNIQUE KEY unique_invig_workload (invigilator_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ===============================================
-- Table: allocation_history (for audit trail)
-- ===============================================
CREATE TABLE allocation_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    allocation_type ENUM('SEATING', 'INVIGILATOR') NOT NULL,
    algorithm_used VARCHAR(50),
    total_allocated INT,
    execution_time_ms BIGINT,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(id) ON DELETE CASCADE,
    INDEX idx_history_exam (exam_id),
    INDEX idx_history_type (allocation_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
