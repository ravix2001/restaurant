package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.SizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final SizeGroupRepository sizeGroupRepository;

    public SizeServiceImpl(SizeRepository sizeRepository, SizeGroupRepository sizeGroupRepository) {
        this.sizeRepository = sizeRepository;
        this.sizeGroupRepository = sizeGroupRepository;
    }

    @Override
    public SizeDB create(SizeDTO sizeDTO) {
        Long sizeGroupId = sizeDTO.getSizeGroupId();
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(sizeGroupId).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        SizeDB sizeDB = new SizeDB();
        sizeDB.setName(sizeDTO.getName());
        sizeDB.setSizeGroupDB(sizeGroupDB);
        return sizeRepository.save(sizeDB);
    }

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
