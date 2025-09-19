package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    /**
     * Don't touch this - This is working
     */
//    @Override
//    public MenuDTO getMenuInfo(Long menuId) {
//        // Validate the menu exists
//        MenuDB menuDB = menuRepository.findById(menuId)
//                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));
//
//        // Fetch the sizes from the SizeGroup
//        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(sizeDB -> {
//                    SizeDTO sizeDTO = new SizeDTO();
//                    sizeDTO.setId(sizeDB.getId());
//                    sizeDTO.setName(sizeDB.getName());
//                    sizeDTO.setSizeGroupId(sizeDB.getSizeGroupDB().getId());
//                    return sizeDTO;
//                })
//                .collect(Collectors.toList());
//
//        // Fetch selected menu options
//        List<MenuOptionDB> selectedMenuOptions = menuOptionRepository.findByMenuId(menuId);
//        List<OptionDTO> selectedOptions = selectedMenuOptions.stream()
//                .map(menuOptionDB -> {
//                    OptionDTO optionDTO = new OptionDTO();
//                    optionDTO.setId(menuOptionDB.getOptionId());
//                    optionDTO.setName(menuOptionDB.getOptionDB().getName());
//                    optionDTO.setOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId());
//                    optionDTO.setSelected(true); // Mark this option as selected
//                    return optionDTO;
//                })
//                .collect(Collectors.toList());
//
//        // Fetch option groups associated with the SizeGroup
//        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(sizeGroupOptionGroupDB -> {
//                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();
//
//                    // Map options within this option group, including selection status
//                    List<OptionDTO> optionDTOs = optionGroupDB.getOptions().stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//                                optionDTO.setOptionGroupId(optionGroupDB.getId());
//                                // Mark option as selected if it matches the selected options
//                                optionDTO.setSelected(selectedOptions.stream()
//                                        .anyMatch(selectedOption -> selectedOption.getId().equals(optionDB.getId())));
//                                return optionDTO;
//                            })
//                            .collect(Collectors.toList());
//
//                    // Map option group details and attach options
//                    OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
//                    optionGroupDTO.setId(optionGroupDB.getId());
//                    optionGroupDTO.setName(optionGroupDB.getName());
//                    optionGroupDTO.setOptions(optionDTOs);
//
//                    return optionGroupDTO;
//                })
//                .collect(Collectors.toList());
//
//        // Create the MenuDTO response
//        MenuDTO menuDTO = new MenuDTO();
//        menuDTO.setId(menuDB.getId());
//        menuDTO.setName(menuDB.getName());
//        menuDTO.setBasePrice(menuDB.getBasePrice());
//        menuDTO.setSizes(sizeDTOs);
//        menuDTO.setMenuOptions(selectedOptions);
//        menuDTO.setOptionGroups(optionGroupDTOs);
//
//        return menuDTO;
//    }

//    @Override
//    public MenuDTO getMenuInfo(Long menuId) {
//
//        MenuDB menuDB = menuRepository.findById(menuId)
//                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));
//
//        // Fetch sizes and prices
//        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> {
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
//                    // Map to match the expected response structure
//                    menuOptionDTO.setName(menuOptionDB.getOptionDB().getName());  // "tomato"
//                    menuOptionDTO.setOptionId(menuOptionDB.getOptionId());        // optionId: 2
//
//                    // Fetch nested options from the same option group
//                    List<OptionDTO> nestedOptions = optionRepository.findByOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId())
//                            .stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//
//                                // Fetch size-prices for each nested option
//                                List<SizeOptionDTO> sizePrices = sizeOptionRepository.findByOptionId(optionDB.getId())
//                                        .stream()
//                                        .map(sizeOptionDB -> {
//                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//                                            sizeOptionDTO.setId(sizeOptionDB.getSizeId()); // Size ID
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
//                    menuOptionDTO.setOptions(nestedOptions);  // This should be "options" in response
//                    return menuOptionDTO;
//                })
//                .collect(Collectors.toList());
//
//        // Fetch option groups and their nested options
//        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(sizeGroupOptionGroupDB -> {
//                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();
//                    List<OptionDTO> optionDTOs = optionGroupDB.getOptions()
//                            .stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//
//                                // Fetch size-prices for each option - filter by sizeGroupOptionGroup
//                                List<SizeOptionDTO> sizePrices = sizeOptionRepository.findBySizeGroupOptionGroupId(sizeGroupOptionGroupDB.getId())
//                                        .stream()
//                                        .filter(sizeOptionDB -> sizeOptionDB.getOptionId().equals(optionDB.getId()))
//                                        .map(sizeOptionDB -> {
//                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//                                            sizeOptionDTO.setId(sizeOptionDB.getSizeId()); // Size ID
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
//        menuDTO.setMenuOptions(menuOptions);  // This maps to "menuOptions" in response
//        menuDTO.setOptionGroups(optionGroupDTOs);
//
//        return menuDTO;
//    }

    @Override
    public MenuDTO getMenuInfo(Long menuId) {

        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));

        // Fetch sizes and prices
        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
                .map(sizeGroup -> {

                    List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroup.getId());
                    List<MenuSizeDB> menuSizes = menuSizeRepository.findByMenuId(menuId);

                    return sizes.stream()
                            .map(sizeDB -> {
                                SizeDTO sizeDTO = new SizeDTO();
                                sizeDTO.setId(sizeDB.getId());
                                sizeDTO.setName(sizeDB.getName());

                                // Find price from MenuSizeDB
                                BigDecimal price = menuSizes.stream()
                                        .filter(menuSizeDB -> menuSizeDB.getSizeId().equals(sizeDB.getId()))
                                        .map(MenuSizeDB::getPrice)
                                        .findFirst()
                                        .orElse(BigDecimal.ZERO);

                                sizeDTO.setPrice(price);
                                return sizeDTO;
                            })
                            .collect(Collectors.toList());
                })
                .orElse(Collections.emptyList());

        // Fetch selected menu options and their nested options
        List<MenuOptionDB> selectedMenuOptions = menuOptionRepository.findByMenuId(menuId);

        List<OptionDTO> menuOptions = selectedMenuOptions.stream()
                .map(menuOptionDB -> {
                    OptionDTO menuOptionDTO = new OptionDTO();
                    menuOptionDTO.setName(menuOptionDB.getOptionDB().getName());
                    menuOptionDTO.setOptionId(menuOptionDB.getOptionId());

                    // Fetch nested options from the same option group
                    List<OptionDTO> nestedOptions = optionRepository.findByOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId())
                            .stream()
                            .map(optionDB -> {
                                OptionDTO optionDTO = new OptionDTO();
                                optionDTO.setId(optionDB.getId());
                                optionDTO.setName(optionDB.getName());

                                // Get size-prices for this specific option
                                List<SizeOptionDTO> sizePrices = sizeOptionRepository.findByOptionId(optionDB.getId())
                                        .stream()
                                        .map(sizeOptionDB -> {
                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
                                            sizeOptionDTO.setId(sizeOptionDB.getSizeId());
                                            sizeOptionDTO.setPrice(sizeOptionDB.getPrice());
                                            return sizeOptionDTO;
                                        })
                                        .collect(Collectors.toList());

                                optionDTO.setSizes(sizePrices);
                                return optionDTO;
                            })
                            .collect(Collectors.toList());

                    menuOptionDTO.setOptions(nestedOptions);
                    return menuOptionDTO;
                })
                .collect(Collectors.toList());

        // Fetch option groups
        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        sizeGroupOptionGroupDB -> sizeGroupOptionGroupDB.getOptionGroupDB().getId(),
                        Function.identity(),
                        (existing, replacement) -> existing // Keep first occurrence, ignore duplicates
                ))
                .values()
                .stream()
                .map(sizeGroupOptionGroupDB -> {
                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();

                    // Fetch options for this option group using repository
                    List<OptionDB> optionsForGroup = optionRepository.findByOptionGroupId(optionGroupDB.getId());

                    List<OptionDTO> optionDTOs = optionsForGroup.stream()
                            .map(optionDB -> {
                                OptionDTO optionDTO = new OptionDTO();
                                optionDTO.setId(optionDB.getId());
                                optionDTO.setName(optionDB.getName());

                                // Get size-prices for this specific option and sizeGroupOptionGroup
                                List<SizeOptionDTO> sizePrices = sizeOptionRepository.findBySizeGroupOptionGroupId(sizeGroupOptionGroupDB.getId())
                                        .stream()
                                        .filter(sizeOptionDB -> sizeOptionDB.getOptionId().equals(optionDB.getId()))
                                        .collect(Collectors.toMap(
                                                SizeOptionDB::getSizeId,
                                                Function.identity(),
                                                (existing, replacement) -> existing // Remove duplicate size entries
                                        ))
                                        .values()
                                        .stream()
                                        .map(sizeOptionDB -> {
                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
                                            sizeOptionDTO.setId(sizeOptionDB.getSizeId());
                                            sizeOptionDTO.setPrice(sizeOptionDB.getPrice());
                                            return sizeOptionDTO;
                                        })
                                        .collect(Collectors.toList());

                                optionDTO.setSizes(sizePrices);
                                return optionDTO;
                            })
                            .collect(Collectors.toList());

                    OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
                    optionGroupDTO.setId(optionGroupDB.getId());
                    optionGroupDTO.setName(optionGroupDB.getName());
                    optionGroupDTO.setOptions(optionDTOs);

                    return optionGroupDTO;
                })
                .collect(Collectors.toList());

        // Construct the final MenuDTO
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menuDB.getId());
        menuDTO.setName(menuDB.getName());
        menuDTO.setBasePrice(menuDB.getBasePrice());
        menuDTO.setSizes(sizeDTOs);
        menuDTO.setMenuOptions(menuOptions);
        menuDTO.setOptionGroups(optionGroupDTOs);

        return menuDTO;
    }

}