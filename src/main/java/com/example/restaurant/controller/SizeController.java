package com.example.restaurant.controller;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.service.SizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService svc) { this.sizeService = svc; }

    @PostMapping("/groups/{groupId}")
    public ResponseEntity<SizeDTO> create(@PathVariable Long groupId, @RequestBody SizeDTO dto) {
        return ResponseEntity.ok(sizeService.create(groupId, dto));
    }

    @GetMapping("/groups/{groupId}")
    public List<SizeDTO> listByGroup(@PathVariable Long groupId) { return sizeService.findBySizeGroup(groupId); }

    @GetMapping("/{id}")
    public ResponseEntity<SizeDTO> get(@PathVariable Long id) { return ResponseEntity.ok(sizeService.getById(id)); }

    @PutMapping("/{id}")
    public ResponseEntity<SizeDTO> update(@PathVariable Long id, @RequestBody SizeDTO dto) { return ResponseEntity.ok(sizeService.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { sizeService.delete(id); return ResponseEntity.noContent().build(); }
}
