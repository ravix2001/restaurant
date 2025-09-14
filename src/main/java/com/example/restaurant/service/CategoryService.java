package com.example.restaurant.service;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.entity.CategoryDB;

import java.util.List;

public interface CategoryService {

    CategoryDB create(CategoryDTO categoryDTO);
    CategoryDB update(CategoryDTO categoryDTO);
    void delete(Long id);
    List<CategoryDB> getAll();
    CategoryDB getById(Long id);

}
