package com.example.restaurant.controller;

import com.example.restaurant.entity.Item;
import com.example.restaurant.entity.Menu;
import com.example.restaurant.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getMenu() {
        return ResponseEntity.ok(itemService.findAll());
    }

    /**
     * We can later add the controllers of item in this menu section according to requirements
     */

}
