package com.example.restaurant.controller;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/size")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/{id}")
    public ResponseEntity<SizeDB> get(@PathVariable Long id) { return ResponseEntity.ok(sizeService.getById(id)); }

    @PutMapping
    public ResponseEntity<SizeDB> update(@RequestBody SizeDTO sizeDTO) { return ResponseEntity.ok(sizeService.update(sizeDTO)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { sizeService.delete(id); return ResponseEntity.noContent().build(); }

}
