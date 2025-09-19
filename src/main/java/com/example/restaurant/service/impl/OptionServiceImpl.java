package com.example.restaurant.service.impl;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.entity.OptionDB;
import com.example.restaurant.repository.OptionRepository;
import com.example.restaurant.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionServiceImpl implements OptionService {

    @Autowired
    private OptionRepository optionRepository;

    @Override
    public OptionDB update(OptionDTO optionDTO) {
        Long id = optionDTO.getId();
        OptionDB optionDB = optionRepository.findById(id).orElseThrow(() -> new RuntimeException("Option not found"));
        optionDB.setName(optionDTO.getName());
        return optionRepository.save(optionDB);
    }

    @Override
    public void delete(Long id) {
        optionRepository.deleteById(id);
    }

    @Override
    public OptionDB getById(Long id) {
        return optionRepository.findById(id).orElseThrow(() -> new RuntimeException("Option not found"));
    }

}
