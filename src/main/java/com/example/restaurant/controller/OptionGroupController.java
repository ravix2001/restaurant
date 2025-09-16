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
    public ResponseEntity<OptionGroupDB> create(@RequestBody OptionGroupDTO optionGroupDTO) {
        return ResponseEntity.ok(optionGroupService.create(optionGroupDTO));
    }

    @GetMapping
    public ResponseEntity<List<OptionGroupDB>> getAll() {
        return ResponseEntity.ok(optionGroupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionGroupDB> get(@PathVariable Long id) { 
        return ResponseEntity.ok(optionGroupService.getById(id)); 
    }

    @PutMapping
    public ResponseEntity<OptionGroupDB> update(@RequestBody OptionGroupDTO optionGroupDTO) { 
        return ResponseEntity.ok(optionGroupService.update(optionGroupDTO)); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { 
        optionGroupService.delete(id); 
        return ResponseEntity.noContent().build(); 
    }
}