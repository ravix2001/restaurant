package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeGroupRepository sizeGroupRepository;

    @Autowired
    private MenuSizeRepository menuSizeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private MenuOptionRepository menuOptionRepository;

    @Autowired
    private SizeGroupOptionGroupRepository sizeGroupOptionGroupRepository;

    @Autowired
    private SizeOptionRepository sizeOptionRepository;

    @Override
    public MenuDTO createMenuAndSize(MenuDTO request) {
        // Create and save the Menu
        MenuDB menu = new MenuDB();
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setBasePrice(request.getBasePrice());

        // Get Category
        CategoryDB category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        menu.setCategory(category);

        // Get SizeGroup
        SizeGroupDB sizeGroup = sizeGroupRepository.findById(request.getSizeGroupId())
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        menu.setSizeGroup(sizeGroup);
        MenuDB savedMenu = menuRepository.save(menu);

        // Find the sizes of the above sizeGroup
        List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroup.getId());

        // Set the sizes to the respective menu
        List<MenuSizeDB> menuSizes = new ArrayList<>();
        List<MenuSizeDTO> menuSizeDTOs = new ArrayList<>();

        for (SizeDB sizeDB : sizes) {
            MenuSizeDB menuSizeDB = new MenuSizeDB();
            menuSizeDB.setMenuDB(savedMenu);
            menuSizeDB.setSizeDB(sizeDB);
            menuSizes.add(menuSizeDB);

            // Create corresponding DTO
            MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
            menuSizeDTO.setMenuId(savedMenu.getId());
            menuSizeDTO.setSizeId(sizeDB.getId());
            menuSizeDTO.setPrice(menuSizeDB.getPrice());
            menuSizeDTOs.add(menuSizeDTO);
        }

        menuSizeRepository.saveAll(menuSizes);

        // Build and return MenuDTO
        MenuDTO response = new MenuDTO();
        response.setId(savedMenu.getId());
        response.setName(savedMenu.getName());
        response.setDescription(savedMenu.getDescription());
        response.setBasePrice(savedMenu.getBasePrice());
        response.setCategoryId(savedMenu.getCategory().getId());
        response.setSizeGroupId(savedMenu.getSizeGroup().getId());
        response.setMenuSizes(menuSizeDTOs);

        return response;
    }

    @Override
    @Transactional
    public MenuDTO updateMenuAndSize(MenuDTO menuDTO) {

        Long menuId = menuDTO.getId();

        MenuDB existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found "));

        // Update menu fields
        existingMenu.setName(menuDTO.getName());
        existingMenu.setDescription(menuDTO.getDescription());
        existingMenu.setBasePrice(menuDTO.getBasePrice());

        // Update Category if changed
        if (menuDTO.getCategoryId() != null && !menuDTO.getCategoryId().equals(existingMenu.getCategory().getId())) {
            CategoryDB category = categoryRepository.findById(menuDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingMenu.setCategory(category);
        }

        // Update SizeGroup if changed
        SizeGroupDB newSizeGroup = null;
        boolean sizeGroupChanged = false;

        if (menuDTO.getSizeGroupId() != null) {
            if (existingMenu.getSizeGroup() == null || !menuDTO.getSizeGroupId().equals(existingMenu.getSizeGroup().getId())) {
                newSizeGroup = sizeGroupRepository.findById(menuDTO.getSizeGroupId())
                        .orElseThrow(() -> new RuntimeException("SizeGroup not found"));
                existingMenu.setSizeGroup(newSizeGroup);
                sizeGroupChanged = true;
            } else {
                newSizeGroup = existingMenu.getSizeGroup();
            }
        }

        // Save updated menu
        MenuDB savedMenu = menuRepository.save(existingMenu);

        // Handle MenuSize updates if SizeGroup changed
        List<MenuSizeDTO> menuSizeDTOs = new ArrayList<>();

        if (sizeGroupChanged) {
            // Delete existing MenuSizes
            List<MenuSizeDB> existingMenuSizes = menuSizeRepository.findByMenuId(menuId);
            if (!existingMenuSizes.isEmpty()) {
                menuSizeRepository.deleteAll(existingMenuSizes);
            }

            // Create new MenuSizes for the new SizeGroup
            List<SizeDB> sizes = sizeRepository.findBySizeGroupId(newSizeGroup.getId());
            List<MenuSizeDB> newMenuSizes = new ArrayList<>();

            for (SizeDB sizeDB : sizes) {
                MenuSizeDB menuSizeDB = new MenuSizeDB();
                menuSizeDB.setMenuDB(savedMenu);
                menuSizeDB.setSizeDB(sizeDB);
                newMenuSizes.add(menuSizeDB);

                // Create corresponding DTO
                MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
                menuSizeDTO.setMenuId(savedMenu.getId());
                menuSizeDTO.setSizeId(sizeDB.getId());
                menuSizeDTO.setPrice(menuSizeDB.getPrice());
                menuSizeDTOs.add(menuSizeDTO);
            }

            menuSizeRepository.saveAll(newMenuSizes);
        } else {
            // SizeGroup didn't change, get existing MenuSizes
            List<SizeDTO> existingSizes = menuSizeRepository.findSizesWithPriceByMenuId(menuId);
            for (SizeDTO sizeDTO : existingSizes) {
                MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
                menuSizeDTO.setMenuId(savedMenu.getId());
                menuSizeDTO.setSizeId(sizeDTO.getId());
                menuSizeDTO.setPrice(sizeDTO.getPrice());
                menuSizeDTOs.add(menuSizeDTO);
            }
        }

        // Build and return MenuDTO
        MenuDTO responseDTO = new MenuDTO();
        responseDTO.setId(savedMenu.getId());
        responseDTO.setName(savedMenu.getName());
        responseDTO.setDescription(savedMenu.getDescription());
        responseDTO.setBasePrice(savedMenu.getBasePrice());
        responseDTO.setCategoryId(savedMenu.getCategory().getId());
        responseDTO.setSizeGroupId(savedMenu.getSizeGroup() != null ? savedMenu.getSizeGroup().getId() : null);
        responseDTO.setMenuSizes(menuSizeDTOs);

        return responseDTO;
    }

    @Override
    public List<MenuDTO> getAllMenu() {
        List<MenuDB> menus = menuRepository.findAll();
        List<MenuDTO> menuDTOList = new ArrayList<>();

        for (MenuDB menu : menus) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setId(menu.getId());
            menuDTO.setName(menu.getName());
            menuDTO.setBasePrice(menu.getBasePrice());
            menuDTO.setCategoryId(menu.getCategory().getId());

            Long menuId = menu.getId();

            List<MenuSizeDB> menuSizeDBList = menuSizeRepository.findByMenuId(menuId);

            List<MenuSizeDTO> menuSizeDTOs = new ArrayList<>();
            for (MenuSizeDB menuSizeDB : menuSizeDBList) {
                MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
                menuSizeDTO.setId(menuSizeDB.getId());
                menuSizeDTO.setPrice(menuSizeDB.getPrice());
                menuSizeDTO.setMenuId(menuSizeDB.getMenuDB().getId());
                menuSizeDTO.setSizeId(menuSizeDB.getSizeDB().getId());
                menuSizeDTOs.add(menuSizeDTO);
            }

            menuDTO.setMenuSizes(menuSizeDTOs);

            if (!menuSizeDBList.isEmpty()) {
                SizeDB firstSize = menuSizeDBList.get(0).getSizeDB();
                if (firstSize.getSizeGroupDB() != null) {
                    menuDTO.setSizeGroupId(firstSize.getSizeGroupDB().getId());
                }
            }

            menuDTOList.add(menuDTO);
        }

        return menuDTOList;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {

        List<CategoryDTO> categories = categoryRepository.getAllCategories();

        List<MenuDB> allMenus = menuRepository.findAll();

        Map<Long, List<MenuDB>> categoryIdMenusMap = allMenus.stream()
                .collect(Collectors.groupingBy(MenuDB::getCategoryId));

        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (CategoryDTO category : categories) {

            List<MenuDB> menus = categoryIdMenusMap.get(category.getId());
            List<MenuDTO> menuDTOList = new ArrayList<>();

            if (menus != null) {
                for (MenuDB menu : menus) {
                    MenuDTO menuDTO = new MenuDTO();
                    menuDTO.setId(menu.getId());
                    menuDTO.setName(menu.getName());
                    menuDTO.setBasePrice(menu.getBasePrice());
                    menuDTO.setCategoryId(category.getId());

                    List<MenuSizeDB> menuSizeDBs = menuSizeRepository.findByMenuId(menu.getId());
                    List<MenuSizeDTO> menuSizeDTOList = new ArrayList<>();

                    for (MenuSizeDB menuSizeDB : menuSizeDBs) {
                        MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
                        menuSizeDTO.setId(menuSizeDB.getId());
                        menuSizeDTO.setPrice(menuSizeDB.getPrice());
                        menuSizeDTO.setMenuId(menuSizeDB.getMenuDB().getId());
                        menuSizeDTO.setSizeId(menuSizeDB.getSizeDB().getId());
                        menuSizeDTOList.add(menuSizeDTO);
                    }

                    if (!menuSizeDBs.isEmpty()) {
                        SizeDB firstSize = menuSizeDBs.get(0).getSizeDB();
                        if (firstSize.getSizeGroupDB() != null) {
                            menuDTO.setSizeGroupId(firstSize.getSizeGroupDB().getId());
                        }
                    }

                    menuDTO.setMenuSizes(menuSizeDTOList);
                    menuDTOList.add(menuDTO);
                }
            }
            category.setMenus(menuDTOList);
            categoryDTOList.add(category);
        }

        return categoryDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO getMenuWithOptions(Long menuId) {
        // Find the menu
        MenuDB menuDB = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuId));

        // Fetch the options associated with the menu via MenuOptionDB
        List<MenuOptionDB> menuOptions = menuOptionRepository.findByMenuId(menuId);

        // Convert MenuDB to MenuDTO
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(menuDB.getId());
        menuDTO.setName(menuDB.getName());
        menuDTO.setDescription(menuDB.getDescription());
        menuDTO.setBasePrice(menuDB.getBasePrice());
        menuDTO.setCategoryId(menuDB.getCategory().getId());

        // Convert MenuOptionDB -> OptionDTO
        List<OptionDTO> optionDTOs = menuOptions.stream()
                .map(menuOption -> {
                    OptionDB optionDB = menuOption.getOptionDB();
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setId(optionDB.getId());
                    optionDTO.setName(optionDB.getName());
                    optionDTO.setOptionGroupId(optionDB.getOptionGroupId());
//                    optionDTO.setSelected(true);      // boolean
                    optionDTO.setIsSelected(true);      // Boolean
                    return optionDTO;
                })
                .collect(Collectors.toList());

        // Set the options to the MenuDTO
        menuDTO.setMenuOptions(optionDTOs);

        return menuDTO;
    }

    @Override
    @Transactional
    public MenuDTO handleMenuOptions(MenuDTO menuDTO) {
        System.out.println("=== DEBUG: handleMenuOptions called ===");
        System.out.println("Menu ID: " + menuDTO.getId());
        System.out.println("MenuOptions size: " + (menuDTO.getMenuOptions() != null ? menuDTO.getMenuOptions().size() : "null"));
        System.out.println("RemovedOptions size: " + (menuDTO.getRemovedOptions() != null ? menuDTO.getRemovedOptions().size() : "null"));

        // Validate that the menu exists
        MenuDB menuDB = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuDTO.getId()));

        System.out.println("Menu found: " + menuDB.getName());

        // Retrieve existing menu options
        List<MenuOptionDB> existingMenuOptions = menuOptionRepository.findByMenuId(menuDTO.getId());
        System.out.println("Existing menu options count: " + existingMenuOptions.size());

        // Process options to add or update
        if (menuDTO.getMenuOptions() != null && !menuDTO.getMenuOptions().isEmpty()) {
            System.out.println("Processing options to add...");
            for (OptionDTO optionDTO : menuDTO.getMenuOptions()) {
                System.out.println("Processing option ID: " + optionDTO.getId() + ", Name: " + optionDTO.getName());

                // Check if the option already exists for the menu
                MenuOptionDB menuOptionDB = existingMenuOptions.stream()
                        .filter(existingOption -> existingOption.getOptionDB().getId().equals(optionDTO.getId()))
                        .findFirst()
                        .orElse(null);

                if (menuOptionDB == null) {
                    System.out.println("Option not found in existing, creating new link...");
                    // Add new option to the menu
                    menuOptionDB = new MenuOptionDB();
                    menuOptionDB.setMenuDB(menuDB);

                    OptionDB optionDB = optionRepository.findById(optionDTO.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Option not found for ID: " + optionDTO.getId()));
                    System.out.println("Option DB found: " + optionDB.getName());

                    menuOptionDB.setOptionDB(optionDB);
                    MenuOptionDB savedMenuOption = menuOptionRepository.save(menuOptionDB);
                    System.out.println("MenuOption saved with ID: " + savedMenuOption.getId());
                } else {
                    System.out.println("Option already exists for this menu");
                }

                // Mark this option as selected
//                optionDTO.setSelected(true);      // boolean
                optionDTO.setIsSelected(true);      // Boolean
            }
        }

        // Process options to remove
        if (menuDTO.getRemovedOptions() != null && !menuDTO.getRemovedOptions().isEmpty()) {
            System.out.println("Processing options to remove...");
            for (OptionDTO removedOption : menuDTO.getRemovedOptions()) {
                System.out.println("Removing option ID: " + removedOption.getId());
                // Remove option from the menu
                menuOptionRepository.deleteByMenuIdAndOptionId(menuDTO.getId(), removedOption.getId());
                System.out.println("Option removed from menu");

                // Mark this option as not selected
//                removedOption.setSelected(false);
                removedOption.setIsSelected(false);
            }
        }

        // Prepare response options from database to ensure fresh and correct results
        List<OptionDTO> responseOptions = menuOptionRepository.findByMenuId(menuDTO.getId()).stream()
                .map(menuOptionDB -> {
                    OptionDTO optionDTO = new OptionDTO();
                    OptionDB optionDB = menuOptionDB.getOptionDB();
                    optionDTO.setId(optionDB.getId());
                    optionDTO.setName(optionDB.getName());
                    optionDTO.setOptionGroupId(optionDB.getOptionGroupId());
//                    optionDTO.setSelected(true);
                    optionDTO.setIsSelected(true);
                    return optionDTO;
                })
                .collect(Collectors.toList());

        System.out.println("Final response options count: " + responseOptions.size());

        // Build the response DTO
        MenuDTO responseDTO = new MenuDTO();
        responseDTO.setId(menuDB.getId());
        responseDTO.setName(menuDB.getName());
        responseDTO.setDescription(menuDB.getDescription());
        responseDTO.setBasePrice(menuDB.getBasePrice());
        responseDTO.setMenuOptions(responseOptions);
        responseDTO.setRemovedOptions(menuDTO.getRemovedOptions());

        System.out.println("=== DEBUG: handleMenuOptions completed ===");
        return responseDTO;
    }

    @Override
    public MenuDTO getMenuOptionsDetailed(Long id) {

        MenuDB menuDB = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        List<SizeGroupOptionGroupDB> sizeGroupOptionGroupDBList =
                sizeGroupOptionGroupRepository.findBySizeGroupId(menuDB.getSizeGroup().getId());


        List<OptionDB> allOptions = new ArrayList<>();
        for (SizeGroupOptionGroupDB sizeGroupOptionGroupDB : sizeGroupOptionGroupDBList) {
            allOptions.addAll(optionRepository.findByOptionGroupId(sizeGroupOptionGroupDB.getOptionGroupId()));
        }

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(id);

        List<OptionDTO> optionDTOList = new ArrayList<>();
        List<MenuOptionDB> menuOptionDBList = menuOptionRepository.findByMenuId(menuDB.getId());

        for (OptionDB optionDB : allOptions) {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(optionDB.getId());
            optionDTO.setName(optionDB.getName());

            boolean isSelected = false;
            for (MenuOptionDB menuOptionDB : menuOptionDBList) {
                if (menuOptionDB.getOptionDB().getId().equals(optionDB.getId())) {
//                    optionDTO.setSelected(true);
                    isSelected = true;
                    break;
                }
            }
//            optionDTO.setSelected(isSelected);
            optionDTO.setIsSelected(isSelected);
            optionDTOList.add(optionDTO);
        }
        menuDTO.setMenuOptions(optionDTOList);
        return menuDTO;

    }

    @Override
    public MenuDTO handleMenuOptionsDetailed(MenuDTO menuDTO) {
        Long menuId = menuDTO.getId();

        MenuDB menuDB = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        List<OptionDTO> submittedOptions = menuDTO.getMenuOptions();

//        List<MenuOptionDB> savedOptions = menuOptionRepository.findAllByMenuId(menuId);

        for (OptionDTO submittedOptionDTO : submittedOptions) {

            MenuOptionDB menuOptionDB = menuOptionRepository.findByOptionId(submittedOptionDTO.getId());

            if (menuOptionDB == null && submittedOptionDTO.getIsSelected()) {
                MenuOptionDB newOption = new MenuOptionDB();
                newOption.setMenuId(menuId);
                newOption.setMenuDB(menuDB);
                OptionDB optionDB = optionRepository.findById(submittedOptionDTO.getId()).orElseThrow(() -> new RuntimeException("Option not found"));
                newOption.setOptionDB(optionDB);
                menuOptionRepository.save(newOption);
            } else if (menuOptionDB != null && !submittedOptionDTO.getIsSelected()) {
                menuOptionRepository.delete(menuOptionDB);
            }
        }

        return menuDTO;
    }

    // Thursday

    @Override
    public SizeGroupOptionGroupDTO getExtraPrices(Long sizeGroupOptionGroupId) {

        // Validate if group exists
        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = sizeGroupOptionGroupRepository.findById(sizeGroupOptionGroupId)
                .orElseThrow(() -> new RuntimeException("SizeGroupOptionGroup not found"));

        SizeGroupDB sizeGroupDB = sizeGroupOptionGroupDB.getSizeGroupDB();
        OptionGroupDB optionGroupDB = sizeGroupOptionGroupDB.getOptionGroupDB();

        // Fetch from DB
        List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroupDB.getId());
        List<OptionDB> options = optionRepository.findByOptionGroupId(optionGroupDB.getId());
        List<SizeOptionDB> sizeOptions = sizeOptionRepository.findBySizeGroupOptionGroupId(sizeGroupOptionGroupId);

        // Build response - using OptionDTO instead of SizeOptionDTO
        List<OptionDTO> optionsList = new ArrayList<>();

        for (OptionDB option : options) {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(option.getId());
//            optionDTO.setName(option.getName()); // Set the option name
//            optionDTO.setOptionGroupId(option.getOptionGroupId()); // Set the option group ID

            List<SizeOptionDTO> sizesList = new ArrayList<>();
            for (SizeDB size : sizes) {
                SizeOptionDTO sizeDTO = new SizeOptionDTO();
//                sizeDTO.setSizeId(size.getId()); // Use setSizeId instead of setId
//                sizeDTO.setOptionId(option.getId()); // Set the option ID

                // Match price from DB
                BigDecimal price = sizeOptions.stream()
                        .filter(so -> so.getSizeDB().getId().equals(size.getId())
                                && so.getOptionDB().getId().equals(option.getId()))
                        .map(SizeOptionDB::getPrice)
                        .findFirst()
                        .orElse(BigDecimal.ZERO);

                sizeDTO.setPrice(price);

                // Optionally set the SizeOption ID if found
                sizeOptions.stream()
                        .filter(so -> so.getSizeDB().getId().equals(size.getId())
                                && so.getOptionDB().getId().equals(option.getId()))
                        .findFirst()
                        .ifPresent(so -> sizeDTO.setId(so.getId()));

                sizesList.add(sizeDTO);
            }

            optionDTO.setSizes(sizesList);
            optionsList.add(optionDTO);
        }

        SizeGroupOptionGroupDTO response = new SizeGroupOptionGroupDTO();
//        response.setSizeGroupOptionGroupId(sizeGroupOptionGroupId);
//        response.setSizeGroupId(sizeGroupDB.getId()); // Add size group ID
//        response.setOptionGroupId(optionGroupDB.getId()); // Add option group ID
        response.setOptions(optionsList);

        return response;
    }

    @Override
    @Transactional
    public SizeGroupOptionGroupDTO handleExtraPrices(SizeGroupOptionGroupDTO request) {

        Long sizeGroupOptionGroupId = request.getSizeGroupOptionGroupId();

        SizeGroupOptionGroupDB sizeGroupOptionGroup = sizeGroupOptionGroupRepository.findById(sizeGroupOptionGroupId)
                .orElseThrow(() -> new RuntimeException("SizeGroupOptionGroup not found"));

        List<OptionDTO> optionsList = new ArrayList<>();

        for (OptionDTO optionFromRequest : request.getOptions()) {
            Long optionId = optionFromRequest.getId();
            System.out.println("Processing option with ID: " + optionId);

            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(optionId);
            optionDTO.setName(optionFromRequest.getName());

            List<SizeOptionDTO> sizesList = new ArrayList<>();

            if (optionFromRequest.getSizes() != null) {
                for (SizeOptionDTO sizeFromRequest : optionFromRequest.getSizes()) {
                    Long sizeId = sizeFromRequest.getId(); // Using getId() based on your working getExtraPrices
                    BigDecimal price = sizeFromRequest.getPrice();

                    System.out.println("Processing SizeOption: SizeId=" + sizeId +
                            ", OptionId=" + optionId +
                            ", Price=" + price +
                            ", SizeGroupOptionGroupId=" + sizeGroupOptionGroupId);

                    // Check if SizeOption already exists
                    Optional<SizeOptionDB> existingSizeOption = sizeOptionRepository
                            .findBySizeIdAndOptionIdAndSizeGroupOptionGroupId(sizeId, optionId, sizeGroupOptionGroupId);

                    SizeOptionDB sizeOption;

                    if (existingSizeOption.isPresent()) {
                        // UPDATE existing record
                        sizeOption = existingSizeOption.get();
                        sizeOption.setPrice(price); // Update the price
                        System.out.println("Updating existing SizeOption with ID: " + sizeOption.getId());
                    } else {
                        // INSERT new record
                        sizeOption = new SizeOptionDB();
                        sizeOption.setPrice(price);
                        sizeOption.setSizeId(sizeId);
                        sizeOption.setOptionId(optionId);
                        sizeOption.setSizeGroupOptionGroupId(sizeGroupOptionGroupId);
                        sizeOption.setSizeGroupOptionGroupDB(sizeGroupOptionGroup);

                        // Set SizeDB and OptionDB references
                        if (sizeRepository != null) {
                            SizeDB sizeDB = sizeRepository.findById(sizeId)
                                    .orElseThrow(() -> new RuntimeException("Size not found with id: " + sizeId));
                            sizeOption.setSizeDB(sizeDB);
                        }

                        if (optionRepository != null) {
                            OptionDB optionDB = optionRepository.findById(optionId)
                                    .orElseThrow(() -> new RuntimeException("Option not found with id: " + optionId));
                            sizeOption.setOptionDB(optionDB);
                        }
                        System.out.println("Creating new SizeOption record");
                    }

                    // Save (insert or update)
                    SizeOptionDB savedEntity = sizeOptionRepository.save(sizeOption);
                    System.out.println("Successfully saved SizeOption with ID: " + savedEntity.getId() +
                            ", Price: " + savedEntity.getPrice());

                    // Create DTO for response
                    SizeOptionDTO sizeDTO = new SizeOptionDTO();
                    sizeDTO.setPrice(price);
                    sizeDTO.setId(savedEntity.getId());
//                    sizeDTO.setSizeId(sizeId); // Use setSizeId()
//                    sizeDTO.setOptionId(optionId); // Also set optionId
                    sizesList.add(sizeDTO);
                }
            }

            optionDTO.setSizes(sizesList);
            optionsList.add(optionDTO);
        }

        // Build response DTO
        SizeGroupOptionGroupDTO response = new SizeGroupOptionGroupDTO();
        response.setSizeGroupOptionGroupId(sizeGroupOptionGroupId);
//        response.setSizeGroupId(sizeGroupOptionGroup.getSizeGroupId());
//        response.setOptionGroupId(sizeGroupOptionGroup.getOptionGroupId());
        response.setOptions(optionsList);

        System.out.println("Successfully processed all SizeOption records");
        return response;
    }

}