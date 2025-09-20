package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.dto.SizeGroupDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.SizeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SizeGroupServiceImpl implements SizeGroupService {

    @Autowired
    private SizeGroupRepository sizeGroupRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public SizeGroupDTO create(SizeGroupDTO request) {

        // Create and save SizeGroup
        SizeGroupDB sizeGroupDB = new SizeGroupDB();
        sizeGroupDB.setId(request.getId());
        sizeGroupDB.setName(request.getName());
        SizeGroupDB saved = sizeGroupRepository.save(sizeGroupDB);

        // Create and save Options
        List<SizeDTO> savedOptions = new ArrayList<>();
        for (SizeDTO sizeDTO : request.getSizes()) {
            SizeDB sizeDB = new SizeDB();
            sizeDB.setName(sizeDTO.getName());
            sizeDB.setSizeGroupDB(saved);
            sizeDB.setSizeGroupId(saved.getId());

            SizeDB savedSize = sizeRepository.save(sizeDB);

            // Map SizeDB to DTO
            SizeDTO savedSizeDTO = new SizeDTO();
            savedSizeDTO.setId(savedSize.getId());
            savedSizeDTO.setName(savedSize.getName());
            savedOptions.add(savedSizeDTO);
        }

        // Map SizeGroupDB back to DTO
        SizeGroupDTO response = new SizeGroupDTO();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setSizes(savedOptions);

        return response;

    }

    @Override
    @Transactional
    public SizeGroupDTO update(SizeGroupDTO request) {

        Long id = request.getId();

        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));

        // Update group name
        sizeGroupDB.setName(request.getName());
        SizeGroupDB savedGroup = sizeGroupRepository.save(sizeGroupDB);

        // Fetch existing options for the group
        List<SizeDB> existingSizes = sizeRepository.findBySizeGroupId(id);

        // Collect request option IDs (non-null ones)
        List<Long> requestSizeIds = request.getSizes().stream()
                .map(SizeDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Delete options that are not in the request
        List<SizeDB> sizesToDelete = existingSizes.stream()
                .filter(existingSize -> !requestSizeIds.contains(existingSize.getId()))
                .collect(Collectors.toList());
        sizeRepository.deleteAll(sizesToDelete);

        // Process request options (update existing or create new)
        List<SizeDTO> updatedSizes = new ArrayList<>();
        for (SizeDTO sizeDTO : request.getSizes()) {
            if (sizeDTO.getId() != null) {
                // Update existing option
                SizeDB sizeDB = existingSizes.stream()
                        .filter(existingSize -> existingSize.getId().equals(sizeDTO.getId()))
                        .findFirst()
                        .orElse(null);

                if (sizeDB != null) {
                    sizeDB.setName(sizeDTO.getName());
                    SizeDB savedSize = sizeRepository.save(sizeDB);

                    SizeDTO updatedDTO = new SizeDTO();
                    updatedDTO.setId(savedSize.getId());
                    updatedDTO.setName(savedSize.getName());
                    updatedSizes.add(updatedDTO);
                }
            } else {
                // Create new size
                SizeDB sizeDB = new SizeDB();
                sizeDB.setName(sizeDTO.getName());
                sizeDB.setSizeGroupDB(savedGroup);
                sizeDB.setSizeGroupId(savedGroup.getId());
                SizeDB savedSize = sizeRepository.save(sizeDB);

                SizeDTO newDTO = new SizeDTO();
                newDTO.setId(savedSize.getId());
                newDTO.setName(savedSize.getName());
                updatedSizes.add(newDTO);
            }
        }

        // Build response DTO
        SizeGroupDTO response = new SizeGroupDTO();
        response.setId(savedGroup.getId());
        response.setName(savedGroup.getName());
        response.setSizes(updatedSizes);

        return response;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));

        // First delete children
        sizeRepository.deleteBySizeGroupId(id);

        // Then delete parent
        sizeGroupRepository.delete(sizeGroupDB);
    }

    @Override
    public List<SizeGroupDTO> findAll() {

        List<SizeGroupDB> sizeGroups = sizeGroupRepository.findAll();

        List<SizeGroupDTO> response = new ArrayList<>();
        for (SizeGroupDB sizeGroupDB : sizeGroups) {
            SizeGroupDTO sizeGroupDTO = new SizeGroupDTO();
            sizeGroupDTO.setId(sizeGroupDB.getId());
            sizeGroupDTO.setName(sizeGroupDB.getName());
            sizeGroupDTO.setSizes(new ArrayList<>());
            response.add(sizeGroupDTO);
            for (SizeDB sizeDB : sizeRepository.findBySizeGroupId(sizeGroupDB.getId())) {
                SizeDTO sizeDTO = new SizeDTO();
                sizeDTO.setId(sizeDB.getId());
                sizeDTO.setName(sizeDB.getName());
                sizeGroupDTO.getSizes().add(sizeDTO);
            }
        }
        return response;
    }

    @Override
    public SizeGroupDTO getById(Long id) {

        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("SizeGroup not found"));

        SizeGroupDTO response = new SizeGroupDTO();
        response.setId(sizeGroupDB.getId());
        response.setName(sizeGroupDB.getName());
        response.setSizes(new ArrayList<>());
        for (SizeDB sizeDB : sizeRepository.findBySizeGroupId(sizeGroupDB.getId())) {
            SizeDTO sizeDTO = new SizeDTO();
            sizeDTO.setId(sizeDB.getId());
            sizeDTO.setName(sizeDB.getName());
            response.getSizes().add(sizeDTO);
        }
        return response;
    }

}