package com.example.restaurant.service;

import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.OptionGroupDB;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OptionGroupService {

    OptionGroupDTO create(OptionGroupDTO optionGroupDTO);
    OptionGroupDTO update(OptionGroupDTO optionGroupDTO);
    void delete(Long id);
    List<OptionGroupDTO> findAll();
    OptionGroupDTO getById(Long id);

}
