package com.example.restaurant.controller;

import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.OptionGroupDB;
import com.example.restaurant.service.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/option-group")
public class OptionGroupController {

    @Autowired
    public OptionGroupService optionGroupService;

    @PostMapping
    public ResponseEntity<OptionGroupDTO> create(@RequestBody OptionGroupDTO optionGroupDTO) {
        return ResponseEntity.ok(optionGroupService.create(optionGroupDTO));
    }

    @PutMapping
    public ResponseEntity<OptionGroupDTO> update(@RequestBody OptionGroupDTO optionGroupDTO) {
        return ResponseEntity.ok(optionGroupService.update(optionGroupDTO));
    }

    @GetMapping
    public ResponseEntity<List<OptionGroupDTO>> getAll() {
        return ResponseEntity.ok(optionGroupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionGroupDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(optionGroupService.getById(id)); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { 
        optionGroupService.delete(id); 
        return ResponseEntity.noContent().build(); 
    }
}