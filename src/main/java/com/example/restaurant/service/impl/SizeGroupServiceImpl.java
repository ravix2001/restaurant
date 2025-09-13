package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.CategoryDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.mapper.SizeGroupMapper;
import com.example.restaurant.repository.CategoryRepository;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeGroupServiceImpl implements SizeGroupService {

    private final SizeGroupRepository repo;
    private final CategoryRepository categoryRepo;
    private final SizeGroupMapper mapper;

    public SizeGroupServiceImpl(SizeGroupRepository repo, CategoryRepository categoryRepo, SizeGroupMapper mapper) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    @Override
    public SizeGroupDTO create(Long categoryId, SizeGroupDTO dto) {
        CategoryDB category = categoryRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        SizeGroupDB sg = mapper.toEntity(dto);
        sg.setCategory(category);
        SizeGroupDB saved = repo.save(sg);
        return mapper.toDTO(saved);
    }

    @Override
    public SizeGroupDTO update(Long id, SizeGroupDTO dto) {
        SizeGroupDB sg = repo.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        sg.setName(dto.getName());
        sg.setDescription(dto.getDescription());
        return mapper.toDTO(repo.save(sg));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<SizeGroupDTO> findByCategory(Long categoryId) {
        return repo.findByCategoryId(categoryId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SizeGroupDTO getById(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
    }
}
