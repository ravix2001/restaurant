package com.example.restaurant.controller;

import com.example.restaurant.entity.Item;
import com.example.restaurant.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
//        return product.map(value -> ResponseEntity.ok(itemService.getProductResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
        return ResponseEntity.ok(item);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getItemByName(@PathVariable String name) {
        Optional<Item> item = itemService.findByName(name);
//        return product.map(value -> ResponseEntity.ok(itemService.getProductResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
        return ResponseEntity.ok(item);
    }

}
