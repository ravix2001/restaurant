package com.example.restaurant.controller;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.dto.SizeOptionGroupDTO;
import com.example.restaurant.service.FoodService;
import com.example.restaurant.service.SizeOptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private SizeOptionGroupService sizeOptionGroupService;

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

    @PutMapping("/linkSizeGroupAndOptionGroup")
    public ResponseEntity<String> linkSizeGroupAndOptionGroup(@RequestBody SizeOptionGroupDTO request) {
        sizeOptionGroupService.linkSizeGroupAndOptionGroup(request);
        return ResponseEntity.ok("Size group and option group linked successfully.");
    }

    @DeleteMapping("/unlinkSizeGroupAndOptionGroup/{sizeGroupOptionGroupId}")
    public ResponseEntity<String> unlinkSizeAndOptionGroup(@PathVariable Long sizeGroupOptionGroupId) {
        sizeOptionGroupService.unlinkSizeGroupAndOptionGroup(sizeGroupOptionGroupId);
        return ResponseEntity.ok("Size group and option group unlinked successfully.");
    }

    @GetMapping("/getMenuWithOptions/{menuId}")
    public ResponseEntity<MenuDTO> getMenuWithOptions(@PathVariable Long menuId) {
        MenuDTO menuDTO = foodService.getMenuWithOptions(menuId);
        return ResponseEntity.ok(menuDTO);
    }

    @PutMapping("/handleMenuOptions")
    public ResponseEntity<MenuDTO> handleMenuOptions(@RequestBody MenuDTO menuDTO) {
        foodService.handleMenuOptions(menuDTO);
        return ResponseEntity.ok(menuDTO);
    }

    @GetMapping("/getMenuOptionsDetailed/{menuId}")
    public ResponseEntity<MenuDTO> getMenuOptionsDetailed(@PathVariable Long menuId) {
        MenuDTO menuDTO = foodService.getMenuOptionsDetailed(menuId);
        return ResponseEntity.ok(menuDTO);
    }

    @PutMapping("/handleMenuOptionsDetailed")
    public ResponseEntity<MenuDTO> handleMenuOptionsDetailed(@RequestBody MenuDTO menuDTO) {
        MenuDTO updatedMenuDTO = foodService.handleMenuOptionsDetailed(menuDTO);
        return ResponseEntity.ok(updatedMenuDTO);
    }
}
