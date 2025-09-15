package com.example.restaurant.service;

import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.entity.MenuDB;

import java.util.List;

public interface FoodService {

    MenuDB create(MenuDTO menuDTO);
    MenuDB update(MenuDTO menuDTO);
    void delete(Long id);
    MenuDB getById(Long id);
    List<MenuDB> getAll();

}
