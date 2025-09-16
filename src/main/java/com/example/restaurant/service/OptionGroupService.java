package com.example.restaurant.service;

import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.OptionGroupDB;

import java.util.List;

public interface OptionGroupService {

    OptionGroupDB create(OptionGroupDTO optionGroupDTO);
    OptionGroupDB update(OptionGroupDTO optionGroupDTO);
    void delete(Long id);
    List<OptionGroupDB> findAll();
    OptionGroupDB getById(Long id);

}
