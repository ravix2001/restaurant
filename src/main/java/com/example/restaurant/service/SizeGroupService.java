package com.example.restaurant.service;

import com.example.restaurant.dto.SizeGroupDTO;

import java.util.List;

public interface SizeGroupService {

    SizeGroupDTO create(SizeGroupDTO sizeGroupDTO);
    SizeGroupDTO update(SizeGroupDTO sizeGroupDTO);
    void delete(Long id);
    List<SizeGroupDTO> findAll();
    SizeGroupDTO getById(Long id);

}
