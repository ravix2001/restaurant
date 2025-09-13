package com.example.restaurant.service;

import com.example.restaurant.dto.SizeDTO;

import java.util.List;

public interface SizeService {

    SizeDTO create(Long sizeGroupId, SizeDTO dto);
    SizeDTO update(Long id, SizeDTO dto);
    void delete(Long id);
    List<SizeDTO> findBySizeGroup(Long sizeGroupId);
    SizeDTO getById(Long id);

}
