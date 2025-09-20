package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
    @Autowired
    private SizeOptionRepository sizeOptionRepository;

    @Override
    public MenuDTO getMenuInfo(Long menuId) {
        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));

        // Build base menu DTO
        MenuDTO dto = new MenuDTO(menuDB.getId(), menuDB.getName(), menuDB.getBasePrice());

        // Sizes
        List<SizeDTO> sizes = menuSizeRepository.findSizesWithPriceByMenuId(menuId);
        dto.setSizes(sizes);

        // Menu Options
        List<OptionDTO> menuOptions = menuOptionRepository.findMenuOptionsByMenuId(menuId);
        for (OptionDTO menuOption : menuOptions) {
            // Nested options (from option group)
            List<OptionDTO> nestedOptions = optionRepository.findOptionsByOptionGroupId(menuOption.getOptionId());
            for (OptionDTO nested : nestedOptions) {
                List<SizeOptionDTO> sizePrices =
                        sizeOptionRepository.findSizePrices(nested.getId(), menuDB.getSizeGroup().getId());
                nested.setSizes(sizePrices);
            }
            menuOption.setOptions(nestedOptions);
        }
        dto.setMenuOptions(menuOptions);

        // Option Groups
        List<OptionGroupDTO> optionGroups = new ArrayList<>();
        if (menuDB.getSizeGroup() != null) {
            optionGroups = sizeGroupOptionGroupRepository.findOptionGroupsBySizeGroupId(menuDB.getSizeGroup().getId());

            for (OptionGroupDTO group : optionGroups) {
                List<OptionDTO> groupOptions = optionRepository.findOptionsByOptionGroupId(group.getId());
                for (OptionDTO groupOption : groupOptions) {
                    List<SizeOptionDTO> sizePrices =
                            sizeOptionRepository.findSizePrices(groupOption.getId(), menuDB.getSizeGroup().getId());
                    groupOption.setSizes(sizePrices);
                }
                group.setOptions(groupOptions);
            }
        }
        dto.setOptionGroups(optionGroups);

        return dto;
    }


//    @Override
//    public MenuDTO getMenuInfo(Long menuId) {
//
//        MenuDB menuDB = menuRepository.findById(menuId)
//                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));
//
//        // Fetch sizes and prices
//        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> {
//
//                    List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroup.getId());
//                    List<MenuSizeDB> menuSizes = menuSizeRepository.findByMenuId(menuId);
//
//                    return sizes.stream()
//                            .map(sizeDB -> {
//                                SizeDTO sizeDTO = new SizeDTO();
//                                sizeDTO.setId(sizeDB.getId());
//                                sizeDTO.setName(sizeDB.getName());
//
//                                // Find price from MenuSizeDB
//                                BigDecimal price = menuSizes.stream()
//                                        .filter(menuSizeDB -> menuSizeDB.getSizeId().equals(sizeDB.getId()))
//                                        .map(MenuSizeDB::getPrice)
//                                        .findFirst()
//                                        .orElse(BigDecimal.ZERO);
//
//                                sizeDTO.setPrice(price);
//                                return sizeDTO;
//                            })
//                            .collect(Collectors.toList());
//                })
//                .orElse(Collections.emptyList());
//
//        // Fetch selected menu options and their nested options
//        List<MenuOptionDB> selectedMenuOptions = menuOptionRepository.findByMenuId(menuId);
//
//        List<OptionDTO> menuOptions = selectedMenuOptions.stream()
//                .map(menuOptionDB -> {
//                    OptionDTO menuOptionDTO = new OptionDTO();
//                    menuOptionDTO.setName(menuOptionDB.getOptionDB().getName());
//                    menuOptionDTO.setOptionId(menuOptionDB.getOptionId());
//
//                    // Fetch nested options from the same option group
//                    List<OptionDTO> nestedOptions = optionRepository.findByOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId())
//                            .stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//
//                                // Get size-prices for this specific option
//                                List<SizeOptionDTO> sizePrices = sizeOptionRepository.findByOptionId(optionDB.getId())
//                                        .stream()
//                                        .map(sizeOptionDB -> {
//                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//                                            sizeOptionDTO.setId(sizeOptionDB.getSizeId());
//                                            sizeOptionDTO.setPrice(sizeOptionDB.getPrice());
//                                            return sizeOptionDTO;
//                                        })
//                                        .collect(Collectors.toList());
//
//                                optionDTO.setSizes(sizePrices);
//                                return optionDTO;
//                            })
//                            .collect(Collectors.toList());
//
//                    menuOptionDTO.setOptions(nestedOptions);
//                    return menuOptionDTO;
//                })
//                .collect(Collectors.toList());
//
//        // Fetch option groups
//        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .collect(Collectors.toMap(
//                        sizeGroupOptionGroupDB -> sizeGroupOptionGroupDB.getOptionGroupDB().getId(),
//                        Function.identity(),
//                        (existing, replacement) -> existing // Keep first occurrence, ignore duplicates
//                ))
//                .values()
//                .stream()
//                .map(sizeGroupOptionGroupDB -> {
//                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();
//
//                    // Fetch options for this option group using repository
//                    List<OptionDB> optionsForGroup = optionRepository.findByOptionGroupId(optionGroupDB.getId());
//
//                    List<OptionDTO> optionDTOs = optionsForGroup.stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//
//                                // Get size-prices for this specific option and sizeGroupOptionGroup
//                                List<SizeOptionDTO> sizePrices = sizeOptionRepository.findBySizeGroupOptionGroupId(sizeGroupOptionGroupDB.getId())
//                                        .stream()
//                                        .filter(sizeOptionDB -> sizeOptionDB.getOptionId().equals(optionDB.getId()))
//                                        .collect(Collectors.toMap(
//                                                SizeOptionDB::getSizeId,
//                                                Function.identity(),
//                                                (existing, replacement) -> existing // Remove duplicate size entries
//                                        ))
//                                        .values()
//                                        .stream()
//                                        .map(sizeOptionDB -> {
//                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//                                            sizeOptionDTO.setId(sizeOptionDB.getSizeId());
//                                            sizeOptionDTO.setPrice(sizeOptionDB.getPrice());
//                                            return sizeOptionDTO;
//                                        })
//                                        .collect(Collectors.toList());
//
//                                optionDTO.setSizes(sizePrices);
//                                return optionDTO;
//                            })
//                            .collect(Collectors.toList());
//
//                    OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
//                    optionGroupDTO.setId(optionGroupDB.getId());
//                    optionGroupDTO.setName(optionGroupDB.getName());
//                    optionGroupDTO.setOptions(optionDTOs);
//
//                    return optionGroupDTO;
//                })
//                .collect(Collectors.toList());
//
//        // Construct the final MenuDTO
//        MenuDTO menuDTO = new MenuDTO();
//        menuDTO.setId(menuDB.getId());
//        menuDTO.setName(menuDB.getName());
//        menuDTO.setBasePrice(menuDB.getBasePrice());
//        menuDTO.setSizes(sizeDTOs);
//        menuDTO.setMenuOptions(menuOptions);
//        menuDTO.setOptionGroups(optionGroupDTOs);
//
//        return menuDTO;
//    }

}