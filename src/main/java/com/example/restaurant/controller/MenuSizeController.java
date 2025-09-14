package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuSizeDTO;
import com.example.restaurant.entity.MenuSizeDB;
import com.example.restaurant.service.MenuSizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu/{menuId}/sizes")
public class MenuSizeController {

    private final MenuSizeService menuSizeService;

    public MenuSizeController(MenuSizeService menuSizeService) { this.menuSizeService = menuSizeService; }

    @PostMapping
    public ResponseEntity<MenuSizeDB> setPrice(@PathVariable Long itemId, @RequestBody MenuSizeDTO menuSizeDTO) {
        return ResponseEntity.ok(menuSizeService.setPrice(itemId, menuSizeDTO));
    }

    @GetMapping
    public List<MenuSizeDB> list(@PathVariable Long menuId) {
        return menuSizeService.listForMenu(menuId);
    }

    @DeleteMapping("/{sizeId}")
    public ResponseEntity<Void> delete(@PathVariable Long menuId, @PathVariable Long sizeId) {
        menuSizeService.removePrice(menuId, sizeId);
        return ResponseEntity.noContent().build();
    }
}
