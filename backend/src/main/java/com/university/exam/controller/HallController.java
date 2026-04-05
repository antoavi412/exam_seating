package com.university.exam.controller;

import com.university.exam.dto.HallDTO;
import com.university.exam.service.HallService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/halls")
@CrossOrigin(origins = "*")
public class HallController {
    
    @Autowired
    private HallService hallService;
    
    @GetMapping
    public ResponseEntity<List<HallDTO>> getAllHalls() {
        return ResponseEntity.ok(hallService.getAllHalls());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<HallDTO>> getActiveHalls() {
        return ResponseEntity.ok(hallService.getActiveHalls());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HallDTO> getHallById(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }
    
    @PostMapping
    public ResponseEntity<HallDTO> createHall(@Valid @RequestBody HallDTO hallDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(hallService.createHall(hallDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HallDTO> updateHall(
            @PathVariable Long id, 
            @Valid @RequestBody HallDTO hallDTO) {
        return ResponseEntity.ok(hallService.updateHall(id, hallDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
        return ResponseEntity.noContent().build();
    }
}
