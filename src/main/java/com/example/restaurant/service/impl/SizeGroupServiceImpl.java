package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class SizeGroupServiceImpl implements SizeGroupService {

    private final SizeGroupRepository sizeGroupRepository;
    private final SizeRepository sizeRepository;

    public SizeGroupServiceImpl(SizeGroupRepository sizeGroupRepository, SizeRepository sizeRepository) {
        this.sizeGroupRepository = sizeGroupRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public SizeGroupDB create(SizeGroupDTO request) {
        SizeGroupDB sizeGroupDB = new SizeGroupDB();
        sizeGroupDB.setName(request.getName());
        SizeGroupDB saved = sizeGroupRepository.save(sizeGroupDB);

        for (SizeDTO sizeDTO : request.getSizes()) {
               SizeDB sizeDB = new SizeDB();
               sizeDB.setName(sizeDTO.getName());
               sizeDB.setSizeGroupDB(sizeGroupDB);

               // Initialize sizes list if null
            if (sizeGroupDB.getSizes() == null) {
                sizeGroupDB.setSizes(new ArrayList<>());
            }
            sizeGroupDB.getSizes().add(sizeDB);
            sizeRepository.save(sizeDB);
        }
        return saved;
    }

    @Override
    public SizeGroupDB update(SizeGroupDTO request) {
        Long id = request.getId();
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        sizeGroupDB.setName(request.getName());

        SizeGroupDB saved = sizeGroupRepository.save(sizeGroupDB);

        // Fetch existing sizes for the group
        List<SizeDB> existingSizes = sizeRepository.findBySizeGroupId(id);

        for (SizeDTO sizeDTO : request.getSizes()) {
            SizeDB sizeDB = existingSizes.stream()
                    .filter(existingSize -> existingSize.getId().equals(sizeDTO.getId()))
                    .findFirst()
                    .orElse(null);

            if (sizeDB != null) {
                // If size exists, update its name
                sizeDB.setName(sizeDTO.getName());
            } else {
                // If size does not exist, create a new one and associate it with the group
                sizeDB = new SizeDB();
                sizeDB.setName(sizeDTO.getName());
                sizeDB.setSizeGroupDB(sizeGroupDB);
                sizeGroupDB.getSizes().add(sizeDB);
                sizeRepository.save(sizeDB);
            }
        }
        return saved;

    }

    @Override
    public void delete(Long id) {
        // Fetch the SizeGroup to ensure it exists
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));

        // This will cascade-delete all associated SizeDB entities in the group
        sizeGroupRepository.delete(sizeGroupDB);
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