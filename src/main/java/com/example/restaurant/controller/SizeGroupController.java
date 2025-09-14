package com.example.restaurant.controller;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size-group")
public class SizeGroupController {

    private final SizeGroupService sizeGroupService;

    public SizeGroupController(SizeGroupService sizeGroupService) {
        this.sizeGroupService = sizeGroupService;
    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<SizeGroupDB> create(@PathVariable Long categoryId, @RequestBody SizeGroupDTO sizeGroupDTO) {
        return ResponseEntity.ok(sizeGroupService.create(categoryId, sizeGroupDTO));
    }

    @GetMapping("/category/{categoryId}")
    public List<SizeGroupDB> listByCategory(@PathVariable Long categoryId) {
        return sizeGroupService.findByCategoryId(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeGroupDB> get(@PathVariable Long id) { return ResponseEntity.ok(sizeGroupService.getById(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<SizeGroupDB> update(@PathVariable Long id, @RequestBody SizeGroupDTO sizeGroupDTO) { return ResponseEntity.ok(sizeGroupService.update(id, sizeGroupDTO)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { sizeGroupService.delete(id); return ResponseEntity.noContent().build(); }
}
