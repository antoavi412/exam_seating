package com.university.exam.service;

import com.university.exam.dto.HallDTO;
import com.university.exam.entity.Hall;
import com.university.exam.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HallService {
    
    @Autowired
    private HallRepository hallRepository;
    
    public List<HallDTO> getAllHalls() {
        return hallRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<HallDTO> getActiveHalls() {
        return hallRepository.findByIsActive(true).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public HallDTO getHallById(Long id) {
        Hall hall = hallRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));
        return convertToDTO(hall);
    }
    
    public HallDTO createHall(HallDTO hallDTO) {
        if (hallRepository.existsByHallCode(hallDTO.getHallCode())) {
            throw new RuntimeException("Hall with code " + hallDTO.getHallCode() + " already exists");
        }
        
        // Validate capacity
        if (hallDTO.getRows() * hallDTO.getColumns() != hallDTO.getCapacity()) {
            throw new RuntimeException("Capacity must equal rows * columns");
        }
        
        Hall hall = new Hall();
        hall.setHallCode(hallDTO.getHallCode());
        hall.setName(hallDTO.getName());
        hall.setCapacity(hallDTO.getCapacity());
        hall.setRows(hallDTO.getRows());
        hall.setColumns(hallDTO.getColumns());
        hall.setBuilding(hallDTO.getBuilding());
        hall.setFloor(hallDTO.getFloor());
        hall.setIsActive(hallDTO.getIsActive() != null ? hallDTO.getIsActive() : true);
        
        Hall saved = hallRepository.save(hall);
        return convertToDTO(saved);
    }
    
    public HallDTO updateHall(Long id, HallDTO hallDTO) {
        Hall hall = hallRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hall not found with id: " + id));
        
        if (!hall.getHallCode().equals(hallDTO.getHallCode()) 
                && hallRepository.existsByHallCode(hallDTO.getHallCode())) {
            throw new RuntimeException("Hall with code " + hallDTO.getHallCode() + " already exists");
        }
        
        if (hallDTO.getRows() * hallDTO.getColumns() != hallDTO.getCapacity()) {
            throw new RuntimeException("Capacity must equal rows * columns");
        }
        
        hall.setHallCode(hallDTO.getHallCode());
        hall.setName(hallDTO.getName());
        hall.setCapacity(hallDTO.getCapacity());
        hall.setRows(hallDTO.getRows());
        hall.setColumns(hallDTO.getColumns());
        hall.setBuilding(hallDTO.getBuilding());
        hall.setFloor(hallDTO.getFloor());
        hall.setIsActive(hallDTO.getIsActive());
        
        Hall updated = hallRepository.save(hall);
        return convertToDTO(updated);
    }
    
    public void deleteHall(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new RuntimeException("Hall not found with id: " + id);
        }
        hallRepository.deleteById(id);
    }
    
    private HallDTO convertToDTO(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setHallCode(hall.getHallCode());
        dto.setName(hall.getName());
        dto.setCapacity(hall.getCapacity());
        dto.setRows(hall.getRows());
        dto.setColumns(hall.getColumns());
        dto.setBuilding(hall.getBuilding());
        dto.setFloor(hall.getFloor());
        dto.setIsActive(hall.getIsActive());
        return dto;
    }
}
