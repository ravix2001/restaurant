package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.CategoryDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.repository.CategoryRepository;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeGroupServiceImpl implements SizeGroupService {

    private final SizeGroupRepository sizeGroupRepository;
    private final CategoryRepository categoryRepository;

    public SizeGroupServiceImpl(SizeGroupRepository sizeGroupRepository, CategoryRepository categoryRepository) {
        this.sizeGroupRepository = sizeGroupRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SizeGroupDB create(Long categoryId, SizeGroupDTO sizeGroupDTO) {
        CategoryDB category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        SizeGroupDB sizeGroupDB = new SizeGroupDB();
        sizeGroupDB.setName(sizeGroupDTO.getName());
        sizeGroupDB.setDescription(sizeGroupDTO.getDescription());
        sizeGroupDB.setCategory(category);
        return sizeGroupRepository.save(sizeGroupDB);
    }

    @Override
    public SizeGroupDB update(Long id, SizeGroupDTO sizeGroupDTO) {
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        sizeGroupDB.setName(sizeGroupDTO.getName());
        sizeGroupDB.setDescription(sizeGroupDTO.getDescription());
        return sizeGroupRepository.save(sizeGroupDB);
    }

    @Override
    public void delete(Long id) {
        sizeGroupRepository.deleteById(id);
    }

    @Override
    public List<SizeGroupDB> findByCategoryId(Long categoryId) {
        return sizeGroupRepository.findByCategoryId(categoryId);
    }

    @Override
    public SizeGroupDB getById(Long id) {
        return sizeGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
    }
}
