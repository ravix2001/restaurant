package com.example.restaurant.service;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;

import java.util.List;

public interface SizeService {

    SizeDB update(SizeDTO sizeDTO);
    void delete(Long id);
    List<SizeDB> findBySizeGroup(Long sizeGroupId);
    SizeDB getById(Long id);

}
