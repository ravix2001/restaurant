package com.example.restaurant.controller;

import com.example.restaurant.entity.Item;
import com.example.restaurant.entity.Menu;
import com.example.restaurant.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getMenu() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @PostMapping("/add-items")
    public ResponseEntity<?> addMenu(@RequestBody Menu menu) {
        return ResponseEntity.ok("Items");
    }

}
