package com.example.restaurant.service;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.entity.OptionDB;

public interface OptionService {

    OptionDB update(OptionDTO optionDTO);
    void delete(Long id);
    OptionDB getById(Long id);

}
