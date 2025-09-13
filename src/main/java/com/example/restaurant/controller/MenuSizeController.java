package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuSizeDTO;
import com.example.restaurant.service.MenuSizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items/{itemId}/sizes")
public class MenuSizeController {

    private final MenuSizeService menuSizeService;

    public MenuSizeController(MenuSizeService svc) { this.menuSizeService = svc; }

    @PostMapping
    public ResponseEntity<MenuSizeDTO> setPrice(@PathVariable Long itemId, @RequestBody MenuSizeDTO dto) {
        return ResponseEntity.ok(menuSizeService.setPrice(itemId, dto));
    }

    @GetMapping
    public List<MenuSizeDTO> list(@PathVariable Long itemId) {
        return menuSizeService.listForItem(itemId);
    }

    @DeleteMapping("/{sizeId}")
    public ResponseEntity<Void> delete(@PathVariable Long itemId, @PathVariable Long sizeId) {
        menuSizeService.removePrice(itemId, sizeId);
        return ResponseEntity.noContent().build();
    }
}
