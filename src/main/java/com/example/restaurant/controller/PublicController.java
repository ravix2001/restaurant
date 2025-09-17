package com.example.restaurant.controller;

import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private PublicService publicService;

    @GetMapping("health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK-running");
    }

    @GetMapping("/menuInfo/{menuId}")
    public ResponseEntity<MenuDTO> getMenuInfo(@PathVariable Long menuId) {
        MenuDTO menuDTO = publicService.getMenuInfo(menuId);
        return ResponseEntity.ok(menuDTO);

    }

}
