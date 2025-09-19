package com.example.restaurant.service.impl;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.OptionDB;
import com.example.restaurant.entity.OptionGroupDB;
import com.example.restaurant.repository.OptionGroupRepository;
import com.example.restaurant.repository.OptionRepository;
import com.example.restaurant.service.OptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OptionGroupServiceImpl implements OptionGroupService {

    @Autowired
    private OptionGroupRepository optionGroupRepository;
    @Autowired
    private OptionRepository optionRepository;

    @Override
    public OptionGroupDTO create(OptionGroupDTO request) {
        // Create and save OptionGroup
        OptionGroupDB optionGroupDB = new OptionGroupDB();
        optionGroupDB.setName(request.getName());
        OptionGroupDB savedGroup = optionGroupRepository.save(optionGroupDB);

        // Create and save Options
        List<OptionDTO> savedOptions = new ArrayList<>();
        for (OptionDTO optionDTO : request.getOptions()) {
            OptionDB optionDB = new OptionDB();
            optionDB.setName(optionDTO.getName());
            optionDB.setOptionGroupDB(savedGroup);
            optionDB.setOptionGroupId(savedGroup.getId());

            OptionDB savedOption = optionRepository.save(optionDB);

            // Map back to DTO
            OptionDTO savedOptionDTO = new OptionDTO();
            savedOptionDTO.setId(savedOption.getId());
            savedOptionDTO.setName(savedOption.getName());
            savedOptions.add(savedOptionDTO);
        }

        // Map OptionGroupDB back to DTO
        OptionGroupDTO response = new OptionGroupDTO();
        response.setId(savedGroup.getId());
        response.setName(savedGroup.getName());
        response.setOptions(savedOptions);

        return response;
    }

    @Override
    @Transactional
    public OptionGroupDTO update(OptionGroupDTO request) {

        Long id = request.getId();
        OptionGroupDB optionGroupDB = optionGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OptionGroup not found"));

        // Update group name
        optionGroupDB.setName(request.getName());
        OptionGroupDB savedGroup = optionGroupRepository.save(optionGroupDB);

        // Fetch existing options for the group
        List<OptionDB> existingOptions = optionRepository.findByOptionGroupId(id);

        // Collect request option IDs (non-null ones)
        List<Long> requestOptionIds = request.getOptions().stream()
                .map(OptionDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Delete options that are not in the request
        List<OptionDB> optionsToDelete = existingOptions.stream()
                .filter(existingOption -> !requestOptionIds.contains(existingOption.getId()))
                .collect(Collectors.toList());
        optionRepository.deleteAll(optionsToDelete);

        // Process request options (update existing or create new)
        List<OptionDTO> updatedOptions = new ArrayList<>();
        for (OptionDTO optionDTO : request.getOptions()) {
            if (optionDTO.getId() != null) {
                // Update existing option
                OptionDB optionDB = existingOptions.stream()
                        .filter(existingOption -> existingOption.getId().equals(optionDTO.getId()))
                        .findFirst()
                        .orElse(null);

                if (optionDB != null) {
                    optionDB.setName(optionDTO.getName());
                    OptionDB savedOption = optionRepository.save(optionDB);

                    OptionDTO updatedDTO = new OptionDTO();
                    updatedDTO.setId(savedOption.getId());
                    updatedDTO.setName(savedOption.getName());
                    updatedOptions.add(updatedDTO);
                }
            } else {
                // Create new option
                OptionDB optionDB = new OptionDB();
                optionDB.setName(optionDTO.getName());
                optionDB.setOptionGroupDB(savedGroup);
                optionDB.setOptionGroupId(savedGroup.getId());
                OptionDB savedOption = optionRepository.save(optionDB);

                OptionDTO newDTO = new OptionDTO();
                newDTO.setId(savedOption.getId());
                newDTO.setName(savedOption.getName());
                updatedOptions.add(newDTO);
            }
        }

        // Build response DTO
        OptionGroupDTO response = new OptionGroupDTO();
        response.setId(savedGroup.getId());
        response.setName(savedGroup.getName());
        response.setOptions(updatedOptions);

        return response;
    }


    @Override
    @Transactional
    public void delete(Long id) {

        OptionGroupDB optionGroupDB = optionGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OptionGroup not found"));

        // First delete children
        optionRepository.deleteByOptionGroupId(id);

        // Then delete parent
        optionGroupRepository.delete(optionGroupDB);
    }

    @Override
    public List<OptionGroupDTO> findAll() {

        List<OptionGroupDB> optionGroups = optionGroupRepository.findAll();

        List<OptionGroupDTO> response = new ArrayList<>();

        for (OptionGroupDB optionGroupDB : optionGroups) {
            OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
            optionGroupDTO.setId(optionGroupDB.getId());
            optionGroupDTO.setName(optionGroupDB.getName());
            optionGroupDTO.setOptions(new ArrayList<>());
            response.add(optionGroupDTO);
            for (OptionDB optionDB : optionRepository.findByOptionGroupId(optionGroupDB.getId())) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(optionDB.getId());
                optionDTO.setName(optionDB.getName());
                optionGroupDTO.getOptions().add(optionDTO);
            }
        }

        return response;
    }

    @Override
    public OptionGroupDTO getById(Long id) {
        OptionGroupDB optionGroupDB = optionGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("OptionGroup not found"));

        OptionGroupDTO response = new OptionGroupDTO();
        response.setId(optionGroupDB.getId());
        response.setName(optionGroupDB.getName());
        response.setOptions(new ArrayList<>());
        for (OptionDB optionDB : optionRepository.findByOptionGroupId(optionGroupDB.getId())) {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(optionDB.getId());
            optionDTO.setName(optionDB.getName());
            response.getOptions().add(optionDTO);
        }

        return response;
    }

}
