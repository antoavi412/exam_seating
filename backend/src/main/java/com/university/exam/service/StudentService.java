package com.university.exam.service;

import com.university.exam.dto.StudentDTO;
import com.university.exam.entity.Department;
import com.university.exam.entity.Student;
import com.university.exam.repository.DepartmentRepository;
import com.university.exam.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return convertToDTO(student);
    }
    
    public StudentDTO createStudent(StudentDTO studentDTO) {
        if (studentRepository.existsByRollNumber(studentDTO.getRollNumber())) {
            throw new RuntimeException("Student with roll number " + studentDTO.getRollNumber() + " already exists");
        }
        
        Department department = departmentRepository.findById(studentDTO.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + studentDTO.getDepartmentId()));
        
        Student student = new Student();
        student.setRollNumber(studentDTO.getRollNumber());
        student.setName(studentDTO.getName());
        student.setDepartment(department);
        student.setYear(studentDTO.getYear());
        student.setEmail(studentDTO.getEmail());
        
        Student saved = studentRepository.save(student);
        return convertToDTO(saved);
    }
    
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        if (!student.getRollNumber().equals(studentDTO.getRollNumber()) 
                && studentRepository.existsByRollNumber(studentDTO.getRollNumber())) {
            throw new RuntimeException("Student with roll number " + studentDTO.getRollNumber() + " already exists");
        }
        
        Department department = departmentRepository.findById(studentDTO.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Department not found with id: " + studentDTO.getDepartmentId()));
        
        student.setRollNumber(studentDTO.getRollNumber());
        student.setName(studentDTO.getName());
        student.setDepartment(department);
        student.setYear(studentDTO.getYear());
        student.setEmail(studentDTO.getEmail());
        
        Student updated = studentRepository.save(student);
        return convertToDTO(updated);
    }
    
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
    
    public List<StudentDTO> getStudentsByDepartment(Long departmentId) {
        return studentRepository.findByDepartmentId(departmentId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setRollNumber(student.getRollNumber());
        dto.setName(student.getName());
        dto.setDepartmentId(student.getDepartment().getId());
        dto.setDepartmentCode(student.getDepartment().getCode());
        dto.setDepartmentName(student.getDepartment().getName());
        dto.setYear(student.getYear());
        dto.setEmail(student.getEmail());
        return dto;
    }
}
