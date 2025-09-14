package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeGroupServiceImpl implements SizeGroupService {

    private final SizeGroupRepository sizeGroupRepository;

    public SizeGroupServiceImpl(SizeGroupRepository sizeGroupRepository) {
        this.sizeGroupRepository = sizeGroupRepository;
    }

    @Override
    public SizeGroupDB create(SizeGroupDTO request) {
        SizeGroupDB sizeGroupDB = new SizeGroupDB();
        sizeGroupDB.setName(request.getName());
        sizeGroupDB.setDescription(request.getDescription());

//        for(SizeDTO size : request.getSizes()){
//
//
//        }

        return sizeGroupRepository.save(sizeGroupDB);
    }

    @Override
    public SizeGroupDB update(SizeGroupDTO sizeGroupDTO) {
        Long id = sizeGroupDTO.getId();
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        sizeGroupDB.setName(sizeGroupDTO.getName());
        sizeGroupDB.setDescription(sizeGroupDTO.getDescription());
        return sizeGroupRepository.save(sizeGroupDB);
    }

    @Override
    public void delete(Long id) {
        sizeGroupRepository.deleteById(id);
    }

    @Override
    public List<SizeGroupDB> findAll() {
        return sizeGroupRepository.findAll();
    }

    @Override
    public SizeGroupDB getById(Long id) {
        return sizeGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
    }
}
