package com.example.restaurant.controller;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) { this.foodService = foodService; }

    @PostMapping("/createMenu")
    public ResponseEntity<String> createMenu(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.createMenuAndSize(menuDTO), HttpStatus.OK);
    }

    @PutMapping("/updateMenu")
    public ResponseEntity<String> updateMenu(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.updateMenuAndSize(menuDTO), HttpStatus.OK);
    }

    @GetMapping("/getAllMenus")
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        return new ResponseEntity<>(foodService.getAllMenu(), HttpStatus.OK);
    }

    @GetMapping("/getAllCategoriesAndMenus")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesAndMenus() {
        return new ResponseEntity<>(foodService.getAllCategory(), HttpStatus.OK);
    }
}
