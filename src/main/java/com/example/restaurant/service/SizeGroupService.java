package com.example.restaurant.service;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeGroupDB;

import java.util.List;

public interface SizeGroupService {

    SizeGroupDB create(SizeGroupDTO sizeGroupDTO);
    SizeGroupDB update(SizeGroupDTO sizeGroupDTO);
    void delete(Long id);
    List<SizeGroupDB> findByCategoryId(Long categoryId);
    SizeGroupDB getById(Long id);

}
