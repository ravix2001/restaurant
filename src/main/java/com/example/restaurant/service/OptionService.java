package com.example.restaurant.service;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.entity.OptionDB;

import java.util.List;

public interface OptionService {

    OptionDB update(OptionDTO optionDTO);
    void delete(Long id);
    List<OptionDB> findByOptionGroup(Long optionGroupId);
    OptionDB getById(Long id);

}
