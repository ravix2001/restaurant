package com.example.restaurant.service;

import com.example.restaurant.dto.SizeGroupDTO;

import java.util.List;

public interface SizeGroupService {

    SizeGroupDTO create(Long categoryId, SizeGroupDTO dto);
    SizeGroupDTO update(Long id, SizeGroupDTO dto);
    void delete(Long id);
    List<SizeGroupDTO> findByCategory(Long categoryId);
    SizeGroupDTO getById(Long id);

}
