package com.example.restaurant.controller;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.service.SizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService sizeService) { this.sizeService = sizeService; }

    @PostMapping("/size-group/{groupId}")
    public ResponseEntity<SizeDB> create(@PathVariable Long groupId, @RequestBody SizeDTO sizeDTO) {
        return ResponseEntity.ok(sizeService.create(groupId, sizeDTO));
    }

    @GetMapping("/size-group/{groupId}")
    public List<SizeDB> listByGroup(@PathVariable Long groupId) { return sizeService.findBySizeGroup(groupId); }

    @GetMapping("/{id}")
    public ResponseEntity<SizeDB> get(@PathVariable Long id) { return ResponseEntity.ok(sizeService.getById(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<SizeDB> update(@PathVariable Long id, @RequestBody SizeDTO sizeDTO) { return ResponseEntity.ok(sizeService.update(id, sizeDTO)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { sizeService.delete(id); return ResponseEntity.noContent().build(); }
}
