package com.example.restaurant.service.impl;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.entity.CategoryDB;
import com.example.restaurant.repository.CategoryRepository;
import com.example.restaurant.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository ;
    }

    @Override
    public CategoryDB create(CategoryDTO categoryDTO) {
        CategoryDB categoryDB = new CategoryDB();
        categoryDB.setName(categoryDTO.getName());
        categoryDB.setDescription(categoryDTO.getDescription());

        return categoryRepository.save(categoryDB);
    }

    @Override
    public CategoryDB update(Long id, CategoryDTO categoryDTO) {
        CategoryDB categoryDB = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryDB.setName(categoryDTO.getName());
        categoryDB.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(categoryDB);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDB> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDB getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
