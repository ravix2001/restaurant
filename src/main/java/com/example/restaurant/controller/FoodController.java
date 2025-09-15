package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) { this.foodService = foodService; }

    @PostMapping
    public ResponseEntity<MenuDB> create(@RequestBody MenuDTO menuDTO) { return ResponseEntity.ok(foodService.create(menuDTO)); }

    @GetMapping
    public List<MenuDB> list() { return foodService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDB> get(@PathVariable Long id) { return ResponseEntity.ok(foodService.getById(id)); }

    @PutMapping
    public ResponseEntity<MenuDB> update(@RequestBody MenuDTO menuDTO) { return ResponseEntity.ok(foodService.update(menuDTO)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { foodService.delete(id); return ResponseEntity.noContent().build(); }
}
