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

    /**
     *
     * This is for modification
     */
//    @Override
//    public MenuDTO getMenuInfo(Long menuId) {
//        // Validate the menu exists
//        MenuDB menuDB = menuRepository.findById(menuId)
//                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));
//
//        // Fetch sizes with prices from MenuSizeDB
//        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> {
//                    List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroup.getId());
//                    List<MenuSizeDB> menuSizes = menuSizeRepository.findByMenuId(menuId);
//
//                    return sizes.stream().map(sizeDB -> {
//                        SizeDTO sizeDTO = new SizeDTO();
//                        sizeDTO.setId(sizeDB.getId());
//                        sizeDTO.setName(sizeDB.getName());
//                        sizeDTO.setSizeGroupId(sizeDB.getSizeGroupDB().getId());
//
//                        // Find price from MenuSizeDB
//                        BigDecimal price = menuSizes.stream()
//                                .filter(ms -> ms.getSizeId().equals(sizeDB.getId()))
//                                .map(MenuSizeDB::getPrice)
//                                .findFirst()
//                                .orElse(BigDecimal.ZERO);
//
//                        sizeDTO.setPrice(price);
//                        return sizeDTO;
//                    }).collect(Collectors.toList());
//                })
//                .orElse(Collections.emptyList());
//
//        // Get selected menu options - we'll structure this as option groups
//        List<MenuOptionDB> selectedMenuOptions = menuOptionRepository.findByMenuId(menuId);
//
//        // Build menuOptions as OptionGroups that contain selected options
//        List<OptionGroupDTO> menuOptionGroups = new ArrayList<>();
//
//        // Group selected options by their option group
//        Map<Long, List<MenuOptionDB>> selectedByGroup = selectedMenuOptions.stream()
//                .collect(Collectors.groupingBy(mo -> mo.getOptionDB().getOptionGroupId()));
//
//        for (Map.Entry<Long, List<MenuOptionDB>> entry : selectedByGroup.entrySet()) {
//            Long optionGroupId = entry.getKey();
//            OptionGroupDB optionGroupDB = optionGroupRepository.findById(optionGroupId).orElse(null);
//
//            if (optionGroupDB != null) {
//                OptionGroupDTO optionGroupDTO = new OptionGroupDTO();
//                optionGroupDTO.setId(optionGroupId);
//                optionGroupDTO.setName(optionGroupDB.getName());
//
//                // Get selected options in this group with size prices
//                List<OptionDTO> optionsWithPrices = entry.getValue().stream()
//                        .map(menuOptionDB -> {
//                            OptionDB optionDB = menuOptionDB.getOptionDB();
//                            OptionDTO optionDTO = new OptionDTO();
//                            optionDTO.setId(optionDB.getId());
//                            optionDTO.setName(optionDB.getName());
//                            optionDTO.setOptionGroupId(optionGroupId);
//                            optionDTO.setSelected(true);
//
//                            // Get size prices for this option
//                            optionDTO.setSizes(getSizePricesForOption(menuDB.getSizeGroup().getId(), optionGroupId));
//
//                            return optionDTO;
//                        })
//                        .collect(Collectors.toList());
//
//                optionGroupDTO.setOptions(optionsWithPrices);
//                menuOptionGroups.add(optionGroupDTO);
//            }
//        }
//
//        // Build optionGroups - all available option groups with their options and size prices
//        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(sizeGroupOptionGroupDB -> {
//                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();
//
//                    // Map options within this option group with size prices
//                    List<OptionDTO> optionDTOs = optionGroupDB.getOptions().stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//                                optionDTO.setOptionGroupId(optionGroupDB.getId());
//
//                                // Check if this option is selected
//                                optionDTO.setSelected(selectedMenuOptions.stream()
//                                        .anyMatch(selectedOption -> selectedOption.getOptionId().equals(optionDB.getId())));
//
//                                // Get size prices for this option
//                                optionDTO.setSizes(getSizePricesForOption(menuDB.getSizeGroup().getId(), optionGroupDB.getId()));
//
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
//
//        // For the structure you want, we'll put the selected option groups in menuOptions
//        // But since MenuDTO.menuOptions is List<OptionDTO>, we need to flatten it
//        List<OptionDTO> flattenedMenuOptions = menuOptionGroups.stream()
//                .flatMap(group -> group.getOptions().stream())
//                .collect(Collectors.toList());
//        menuDTO.setMenuOptions(flattenedMenuOptions);
//
//        menuDTO.setOptionGroups(optionGroupDTOs);
//
//        return menuDTO;
//    }
//
//    // Helper method to get size prices for an option using existing DTOs
//    private List<SizeOptionDTO> getSizePricesForOption(Long sizeGroupId, Long optionGroupId) {
//        // Find the SizeGroupOptionGroup relationship
//        List<SizeGroupOptionGroupDB> relationships = sizeGroupOptionGroupRepository
//                .findBySizeGroupIdAndOptionGroupId(sizeGroupId, optionGroupId);
//
//        if (relationships.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        SizeGroupOptionGroupDB relationship = relationships.get(0);
//
//        // Get size options for this relationship
//        List<SizeOptionDB> sizeOptions = sizeOptionRepository
//                .findBySizeGroupOptionGroupId(relationship.getId());
//
//        // Get sizes in the size group
//        List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroupId);
//
//        return sizes.stream().map(size -> {
//            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//            sizeOptionDTO.setId(size.getId());
//
//            // Find price from sizeOptions
//            BigDecimal price = sizeOptions.stream()
//                    .findFirst()
//                    .map(SizeOptionDB::getPrice)
//                    .orElse(BigDecimal.ZERO);
//
//            sizeOptionDTO.setPrice(price);
//            return sizeOptionDTO;
//        }).collect(Collectors.toList());
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
                    menuOptionDTO.setId(menuOptionDB.getOptionId());
                    menuOptionDTO.setName(menuOptionDB.getOptionDB().getName());

                    // Fetch nested options
                    List<OptionDTO> nestedOptions = optionRepository.findByOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId())
                            .stream()
                            .map(optionDB -> {
                                OptionDTO optionDTO = new OptionDTO();
                                optionDTO.setId(optionDB.getId());
                                optionDTO.setName(optionDB.getName());

                                // Fetch size-prices for each nested option
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

        // Fetch option groups and their nested options
        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
                .orElse(Collections.emptyList())
                .stream()
                .map(sizeGroupOptionGroupDB -> {
                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();
                    List<OptionDTO> optionDTOs = optionGroupDB.getOptions()
                            .stream()
                            .map(optionDB -> {
                                OptionDTO optionDTO = new OptionDTO();
                                optionDTO.setId(optionDB.getId());
                                optionDTO.setName(optionDB.getName());

                                // Fetch size-prices for each option
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
//    @Override
//    public MenuDTO getMenuInfo(Long menuId) {
//        // Validate the menu exists
//        MenuDB menuDB = menuRepository.findById(menuId)
//                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));
//
//        List<SizeDTO> sizeDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(sizeDB -> {
//                    SizeDTO sizeDTO = new SizeDTO();
//                    sizeDTO.setId(sizeDB.getId());
//                    sizeDTO.setName(sizeDB.getName());
//                    // attach price (default or from DB mapping)
//                    sizeDTO.setPrice(BigDecimal.ZERO);
//                    return sizeDTO;
//                })
//                .collect(Collectors.toList());
//
//        List<MenuOptionDB> selectedMenuOptions = menuOptionRepository.findByMenuId(menuId);
//
//        List<OptionDTO> menuOptions = selectedMenuOptions.stream()
//                .map(menuOptionDB -> {
//                    OptionDTO menuOptionDTO = new OptionDTO();
//                    menuOptionDTO.setId(menuOptionDB.getId());
//                    menuOptionDTO.setName(menuOptionDB.getOptionDB().getName());
//
//                    // Now fetch nested options inside this menuOption
//                    List<OptionDTO> nestedOptions = optionRepository.findByOptionGroupId(menuOptionDB.getOptionDB().getOptionGroupId())
//                            .stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//
//                                // Fetch size-prices for this option
//                                List<SizeOptionDTO> optionSizes = sizeOptionRepository.findByOptionId(optionDB.getId())
//                                        .stream()
//                                        .map(sizeOptionDB -> {
//                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//                                            sizeOptionDTO.setId(sizeOptionDB.getSizeDB().getId());
//                                            sizeOptionDTO.setPrice(sizeOptionDB.getPrice());
//                                            return sizeOptionDTO;
//                                        })
//                                        .collect(Collectors.toList());
//
//                                optionDTO.setSizes(optionSizes);
//                                return optionDTO;
//                            })
//                            .collect(Collectors.toList());
//
//                    menuOptionDTO.setOptions(nestedOptions);
//                    return menuOptionDTO;
//                })
//                .collect(Collectors.toList());
//
//        List<OptionGroupDTO> optionGroupDTOs = Optional.ofNullable(menuDB.getSizeGroup())
//                .map(sizeGroup -> sizeGroupOptionGroupRepository.findBySizeGroupId(sizeGroup.getId()))
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(sizeGroupOptionGroupDB -> {
//                    OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();
//
//                    List<OptionDTO> optionDTOs = optionGroupDB.getOptions()
//                            .stream()
//                            .map(optionDB -> {
//                                OptionDTO optionDTO = new OptionDTO();
//                                optionDTO.setId(optionDB.getId());
//                                optionDTO.setName(optionDB.getName());
//
//                                // Fetch size-prices for this option
//                                List<SizeOptionDTO> optionSizes = sizeOptionRepository.findByOptionId(optionDB.getId())
//                                        .stream()
//                                        .map(sizeOptionDB -> {
//                                            SizeOptionDTO sizeOptionDTO = new SizeOptionDTO();
//                                            sizeOptionDTO.setId(sizeOptionDB.getSizeDB().getId());
//                                            sizeOptionDTO.setPrice(sizeOptionDB.getPrice());
//                                            return sizeOptionDTO;
//                                        })
//                                        .collect(Collectors.toList());
//
//                                optionDTO.setSizes(optionSizes);
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