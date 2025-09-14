package com.example.restaurant.service.impl;

import com.example.restaurant.dto.MenuDTO;
import com.example.restaurant.entity.CategoryDB;
import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.repository.CategoryRepository;
import com.example.restaurant.repository.MenuRepository;
import com.example.restaurant.repository.MenuSizeRepository;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;
    private final MenuSizeRepository menuSizeRepository;

    public MenuServiceImpl(MenuRepository menuRepository, CategoryRepository categoryRepository, SizeRepository sizeRepository, MenuSizeRepository menuSizeRepository) {
        this.menuRepository = menuRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
        this.menuSizeRepository = menuSizeRepository;
    }

    @Override
    @Transactional
    public MenuDB create(MenuDTO menuDTO) {
        CategoryDB categoryDB = categoryRepository.findById(menuDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
        MenuDB menuDB = new MenuDB();
        menuDB.setName(menuDTO.getName());
        menuDB.setDescription(menuDTO.getDescription());
        menuDB.setBasePrice(menuDTO.getBasePrice());
        menuDB.setCategory(categoryDB);

        return menuRepository.save(menuDB);
    }

    @Override
    @Transactional
    public MenuDB update(MenuDTO menuDTO) {
        Long id = menuDTO.getId();
        MenuDB menuDB = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menuDB.setName(menuDTO.getName());
        menuDB.setDescription(menuDTO.getDescription());
        menuDB.setBasePrice(menuDTO.getBasePrice());
        menuDB.setCategory(categoryRepository.findById(menuDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")));

        return menuRepository.save(menuDB);
    }

    @Override
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public MenuDB getById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
    }

    @Override
    public List<MenuDB> getAll() {
        return menuRepository.findAll();
    }
}
