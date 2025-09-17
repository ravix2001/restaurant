package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicServiceImpl implements PublicService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private MenuSizeRepository menuSizeRepository;
    @Autowired
    private MenuOptionRepository menuOptionRepository;
    @Autowired
    private OptionGroupRepository optionGroupRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private SizeGroupOptionGroupRepository sizeGroupOptionGroupRepository;

    @Override
    public MenuDTO getMenuInfo(Long menuId) {
        // Validate the menu exists
        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));

        // Fetch the sizes from the SizeGroup
        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
                .map(sizeGroup -> sizeRepository.findBySizeGroupId(sizeGroup.getId()))
                .orElse(Collections.emptyList())
                .stream()
                .map(sizeDB -> {
                    SizeDTO sizeDTO = new SizeDTO();
                    sizeDTO.setId(sizeDB.getId());
                    sizeDTO.setName(sizeDB.getName());
                    sizeDTO.setSizeGroupId(sizeDB.getSizeGroupDB().getId());
                    return sizeDTO;
                })
                .collect(Collectors.toList());

        // Fetch selected menu options
        List<MenuOptionDB> selectedMenuOptions = menuOptionRepository.findByMenuId(menuId);
        List<OptionDTO> selectedOptions = selectedMenuOptions.stream()
                .map(menuOptionDB -> {
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setId(menuOptionDB.getOptionId());
                    optionDTO.setName(menuOptionDB.getOptionDB().getName());
                    optionDTO.setOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId());
                    optionDTO.setSelected(true); // Mark this option as selected
                    return optionDTO;
                })
                .collect(Collectors.toList());

        // Fetch option groups associated with the SizeGroup
        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
                .orElse(Collections.emptyList())
                .stream()
                .map(sizeGroupOptionGroupDB -> {
                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();

                    // Map options within this option group, including selection status
                    List<OptionDTO> optionDTOs = optionGroupDB.getOptions().stream()
                            .map(optionDB -> {
                                OptionDTO optionDTO = new OptionDTO();
                                optionDTO.setId(optionDB.getId());
                                optionDTO.setName(optionDB.getName());
                                optionDTO.setOptionGroupId(optionGroupDB.getId());
                                // Mark option as selected if it matches the selected options
                                optionDTO.setSelected(selectedOptions.stream()
                                        .anyMatch(selectedOption -> selectedOption.getId().equals(optionDB.getId())));
                                return optionDTO;
                            })
                            .collect(Collectors.toList());

                    // Map option group details and attach options
                    OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
                    optionGroupDTO.setId(optionGroupDB.getId());
                    optionGroupDTO.setName(optionGroupDB.getName());
                    optionGroupDTO.setOptions(optionDTOs);

                    return optionGroupDTO;
                })
                .collect(Collectors.toList());

        // Create the MenuDTO response
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menuDB.getId());
        menuDTO.setName(menuDB.getName());
        menuDTO.setBasePrice(menuDB.getBasePrice());
        menuDTO.setSizes(sizeDTOs);
        menuDTO.setMenuOptions(selectedOptions);
        menuDTO.setOptionGroups(optionGroupDTOs);

        return menuDTO;
    }

}