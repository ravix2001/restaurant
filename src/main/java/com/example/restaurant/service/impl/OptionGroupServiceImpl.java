package com.example.restaurant.service.impl;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.OptionDB;
import com.example.restaurant.entity.OptionGroupDB;
import com.example.restaurant.repository.OptionGroupRepository;
import com.example.restaurant.repository.OptionRepository;
import com.example.restaurant.service.OptionGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OptionGroupServiceImpl implements OptionGroupService {

    private final OptionGroupRepository optionGroupRepository;
    private final OptionRepository optionRepository;

    public OptionGroupServiceImpl(OptionGroupRepository optionGroupRepository, OptionRepository optionRepository) {
        this.optionGroupRepository = optionGroupRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    public OptionGroupDB create(OptionGroupDTO request) {
        OptionGroupDB optionGroupDB = new OptionGroupDB();
        optionGroupDB.setName(request.getName());
        OptionGroupDB saved = optionGroupRepository.save(optionGroupDB);

        for (OptionDTO optionDTO : request.getOptions()) {
            OptionDB optionDB = new OptionDB();
            optionDB.setName(optionDTO.getName());
            optionDB.setOptionGroupDB(optionGroupDB);

            // Initialize sizes list if null
            if (optionGroupDB.getOptions() == null) {
                optionGroupDB.setOptions(new ArrayList<>());
            }
            optionGroupDB.getOptions().add(optionDB);

            optionRepository.save(optionDB);
        }
        return saved;
    }

    // this works if you just add the options you want to add in the menu in the RequestBody
    @Override
    public OptionGroupDB update(OptionGroupDTO request) {
        Long id = request.getId();
        OptionGroupDB optionGroupDB = optionGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("OptionGroup not found"));
        optionGroupDB.setName(request.getName());

        OptionGroupDB saved = optionGroupRepository.save(optionGroupDB);

        // Fetch existing options for the group
        List<OptionDB> existingOptions = optionRepository.findByOptionGroupId(id);

        for (OptionDTO optionDTO : request.getOptions()) {
            OptionDB optionDB = existingOptions.stream()
                    .filter(existingOption -> existingOption.getId().equals(optionDTO.getId()))
                    .findFirst()
                    .orElse(null);

            if (optionDB != null) {
                // If option exists, update its name
                optionDB.setName(optionDTO.getName());
            } else {
                // If option does not exist, create a new one and associate it with the group
                optionDB = new OptionDB();
                optionDB.setName(optionDTO.getName());
                optionDB.setOptionGroupDB(optionGroupDB);
//                optionGroupDB.getOptions().add(optionDB);
                optionRepository.save(optionDB);
            }
        }
        return saved;

    }

    // this works if you just add all the options you want to add in the menu in the RequestBody
    // but the old options get deleted and new options with different id and same name are added

//    @Override
//    @Transactional
//    public OptionGroupDB update(OptionGroupDTO request) {
//        Long id = request.getId();
//        OptionGroupDB optionGroupDB = optionGroupRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("OptionGroup not found"));
//
//        // Update group name
//        optionGroupDB.setName(request.getName());
//
//        // Fetch existing options from DB
//        List<OptionDB> existingOptions = optionRepository.findByOptionGroupId(id);
//
//        // Map existing options by ID for quick lookup
//        Map<Long, OptionDB> existingMap = existingOptions.stream()
//                .collect(Collectors.toMap(OptionDB::getId, o -> o));
//
//        // Collect IDs from request
//        Set<Long> requestedIds = new HashSet<>();
//
//        for (OptionDTO optionDTO : request.getOptions()) {
//            if (optionDTO.getId() != null && existingMap.containsKey(optionDTO.getId())) {
//                // Case 1: Update existing
//                OptionDB optionDB = existingMap.get(optionDTO.getId());
//                optionDB.setName(optionDTO.getName());
//                requestedIds.add(optionDB.getId());
//            } else {
//                // Case 2: Insert new
//                OptionDB newOption = new OptionDB();
//                newOption.setName(optionDTO.getName());
//                newOption.setOptionGroupDB(optionGroupDB);
//                optionRepository.save(newOption);
//            }
//        }
//
//        // Case 3: Delete options not present in request
//        for (OptionDB existing : existingOptions) {
//            if (!requestedIds.contains(existing.getId())) {
//                optionRepository.delete(existing);
//            }
//        }
//
//        return optionGroupRepository.save(optionGroupDB);
//    }

    @Override
    public void delete(Long id) {
        // Fetch the OptionGroup to ensure it exists
        OptionGroupDB optionGroupDB = optionGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OptionGroup not found"));

        // This will cascade-delete all associated OptionDB entities in the group
        optionGroupRepository.delete(optionGroupDB);
    }

    @Override
    public List<OptionGroupDB> findAll() {
        return optionGroupRepository.findAll();
    }

    @Override
    public OptionGroupDB getById(Long id) {
        return optionGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("OptionGroup not found"));
    }

}
