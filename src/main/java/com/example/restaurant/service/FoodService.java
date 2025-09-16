package com.example.restaurant.service;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.dto.MenuDTO;

import java.util.List;

public interface FoodService {

    String createMenuAndSize(MenuDTO menuDTO);

    String updateMenuAndSize(MenuDTO menuDTO);

    List<MenuDTO> getAllMenu();

    List<CategoryDTO> getAllCategory();

}
