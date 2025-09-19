package com.example.restaurant.controller;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size-group")
public class SizeGroupController {

    @Autowired
    private SizeGroupService sizeGroupService;

    @PostMapping
    public ResponseEntity<SizeGroupDTO> create(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return ResponseEntity.ok(sizeGroupService.create(sizeGroupDTO));
    }

    @PutMapping
    public ResponseEntity<SizeGroupDTO> update(@RequestBody SizeGroupDTO sizeGroupDTO) {
        return ResponseEntity.ok(sizeGroupService.update(sizeGroupDTO));
    }

    @GetMapping
    public ResponseEntity<List<SizeGroupDTO>> getAll() {
        return ResponseEntity.ok(sizeGroupService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeGroupDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(sizeGroupService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sizeGroupService.delete(id); return ResponseEntity.noContent().build();
    }

}
