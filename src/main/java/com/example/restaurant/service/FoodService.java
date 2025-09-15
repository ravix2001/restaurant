package com.example.restaurant.service;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.MenuDB;

import java.util.List;

public interface FoodService {

//    MenuDB create(MenuDTO menuDTO);
//    MenuDB update(MenuDTO menuDTO);
//    void delete(Long id);
//    MenuDB getById(Long id);
//    List<MenuDB> getAll();

//    SizeGroupDTO createSizeGroupAndSize(SizeGroupDTO sizeGroupDTO);
//
//    SizeGroupDTO updateSizeGroupAndSize(SizeGroupDTO sizeGroupDTO);

    MenuDTO createMenuAndSize(MenuDTO menuDTO);

//    MenuDTO updateMenuAndSize(MenuDTO menuDTO);

    List<MenuDTO> getAllMenu();

    List<CategoryDTO> getAllCategory();

}
