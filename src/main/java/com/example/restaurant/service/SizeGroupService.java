package com.example.restaurant.service;

import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeGroupDB;

import java.util.List;

public interface SizeGroupService {

    SizeGroupDB create(Long categoryId, SizeGroupDTO sizeGroupDTO);
    SizeGroupDB update(Long id, SizeGroupDTO sizeGroupDTO);
    void delete(Long id);
    List<SizeGroupDB> findByCategoryId(Long categoryId);
    SizeGroupDB getById(Long id);

}
