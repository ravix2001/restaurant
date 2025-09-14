package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) { this.menuService = menuService; }

    @PostMapping
    public ResponseEntity<MenuDB> create(@RequestBody MenuDTO menuDTO) { return ResponseEntity.ok(menuService.create(menuDTO)); }

    @GetMapping
    public List<MenuDB> list() { return menuService.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDB> get(@PathVariable Long id) { return ResponseEntity.ok(menuService.getById(id)); }

    @PutMapping
    public ResponseEntity<MenuDB> update(@RequestBody MenuDTO menuDTO) { return ResponseEntity.ok(menuService.update(menuDTO)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { menuService.delete(id); return ResponseEntity.noContent().build(); }
}
