package com.example.restaurant.controller;

import com.example.restaurant.dto.*;
import com.example.restaurant.service.FoodService;
import com.example.restaurant.service.SizeOptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private SizeOptionGroupService sizeOptionGroupService;

    @PostMapping("/createMenu")
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        return new ResponseEntity<>(foodService.createMenuAndSize(menuDTO), HttpStatus.OK);
    }

    @PutMapping("/updateMenu")
    public ResponseEntity<MenuDTO> updateMenu(@RequestBody MenuDTO menuDTO) {
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

    @GetMapping("/extraPrices/{sizeGroupOptionGroupId}")
    public ResponseEntity<SizeGroupOptionGroupDTO> getExtraPrices(@PathVariable Long sizeGroupOptionGroupId) {
        SizeGroupOptionGroupDTO response = foodService.getExtraPrices(sizeGroupOptionGroupId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/extraPrices")
    public ResponseEntity<SizeGroupOptionGroupDTO> handleExtraPrices(@RequestBody SizeGroupOptionGroupDTO request) {
        SizeGroupOptionGroupDTO response = foodService.handleExtraPrices(request);
        return ResponseEntity.ok(response);
    }

}
