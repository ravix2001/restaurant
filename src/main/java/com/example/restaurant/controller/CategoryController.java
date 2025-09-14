package com.example.restaurant.controller;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.entity.CategoryDB;
import com.example.restaurant.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @GetMapping
    public List<CategoryDB> list() { return categoryService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDB> get(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDB> create(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping
    public ResponseEntity<CategoryDB> update(@RequestBody CategoryDTO request) {
        return ResponseEntity.ok(categoryService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
