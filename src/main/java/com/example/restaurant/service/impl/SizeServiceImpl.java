package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public SizeDB update(SizeDTO sizeDTO) {
        Long id = sizeDTO.getId();
        SizeDB sizeDB = sizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Size not found"));
        sizeDB.setName(sizeDTO.getName());
        return sizeRepository.save(sizeDB);
    }

    @Override
    public void delete(Long id) {
        sizeRepository.deleteById(id);
    }

    @Override
    public List<SizeDB> findBySizeGroup(Long sizeGroupId) {
        return sizeRepository.findBySizeGroupId(sizeGroupId);
    }

    @Override
    public SizeDB getById(Long id) {
        return sizeRepository.findById(id).orElseThrow(() -> new RuntimeException("Size not found"));
    }
}
