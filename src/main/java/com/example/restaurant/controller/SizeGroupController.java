package com.example.restaurant.controller;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size-groups")
public class SizeGroupController {

    private final SizeGroupService sizeGroupService;

    public SizeGroupController(SizeGroupService svc) { this.sizeGroupService = svc; }

    @PostMapping("/categories/{categoryId}")
    public ResponseEntity<SizeGroupDTO> create(@PathVariable Long categoryId, @RequestBody SizeGroupDTO dto) {
        return ResponseEntity.ok(sizeGroupService.create(categoryId, dto));
    }

    @GetMapping("/categories/{categoryId}")
    public List<SizeGroupDTO> listByCategory(@PathVariable Long categoryId) {
        return sizeGroupService.findByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeGroupDTO> get(@PathVariable Long id) { return ResponseEntity.ok(sizeGroupService.getById(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<SizeGroupDTO> update(@PathVariable Long id, @RequestBody SizeGroupDTO dto) { return ResponseEntity.ok(sizeGroupService.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { sizeGroupService.delete(id); return ResponseEntity.noContent().build(); }
}
