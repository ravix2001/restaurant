package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    private OptionGroupRepository optionGroupRepository;

    @Autowired
    private SizeGroupOptionGroupRepository sizeGroupOptionGroupRepository;

    @Override
    public String createMenuAndSize(MenuDTO menuDTO) {
        //  Create and save the Menu
        MenuDB menu = new MenuDB();
        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        menu.setBasePrice(menuDTO.getBasePrice());

        // Get Category
        CategoryDB category = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        menu.setCategory(category);

        // Get SizeGroup
        SizeGroupDB sizeGroup = sizeGroupRepository.findById(menuDTO.getSizeGroupId())
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        menu.setSizeGroup(sizeGroup);
        MenuDB savedMenu = menuRepository.save(menu);

        // find the sizes of the above sizeGroup
        List<SizeDB> sizes = sizeRepository.findBySizeGroupId(sizeGroup.getId());

        // set the sizes to the respective menu
        List<MenuSizeDB> menuSizes = new ArrayList<>();
        for (SizeDB sizeDB : sizes) {
            MenuSizeDB menuSizeDB = new MenuSizeDB();
            menuSizeDB.setMenuDB(savedMenu);
            menuSizeDB.setSizeDB(sizeDB);
            menuSizes.add(menuSizeDB);
        }
        menuSizeRepository.saveAll(menuSizes);

        return "Success";
    }

    @Override
    public String updateMenuAndSize(MenuDTO menuDTO) {
        MenuDB existingMenu = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new RuntimeException("Menu not found"));
        existingMenu.setName(menuDTO.getName());
        existingMenu.setBasePrice(menuDTO.getBasePrice());

        CategoryDB existingCategory = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingMenu.setCategory(existingCategory);

        SizeGroupDB existingSizeGroup = sizeGroupRepository.findById(menuDTO.getSizeGroupId())
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        existingMenu.setSizeGroup(existingSizeGroup);

        menuRepository.save(existingMenu);

        List<MenuSizeDB> menuSizeDBList = menuSizeRepository.findByMenuId(existingMenu.getId());
        for (MenuSizeDTO menuSizeDTO : menuDTO.getMenuSizes()) {
            for (MenuSizeDB menuSizeDB : menuSizeDBList) {
                if (menuSizeDB.getId().equals(menuSizeDTO.getId())) {
                    menuSizeDB.setPrice(menuSizeDTO.getPrice());
                    menuSizeRepository.save(menuSizeDB);
                }
            }
        }

        return "Success";
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
        List<CategoryDB> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (CategoryDB category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());

            List<MenuDB> menus = menuRepository.findByCategoryId(category.getId());
            List<MenuDTO> menuDTOList = new ArrayList<>();

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

            categoryDTO.setMenus(menuDTOList);
            categoryDTOList.add(categoryDTO);
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
                    optionDTO.setSelected(true);
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
                optionDTO.setSelected(true);
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
                removedOption.setSelected(false);
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
                    optionDTO.setSelected(true); // These are selected options
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
            optionDTO.setSelected(isSelected);
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

            if (menuOptionDB == null && submittedOptionDTO.isSelected()) {
                MenuOptionDB newOption = new MenuOptionDB();
                newOption.setMenuId(menuId);
                newOption.setMenuDB(menuDB);
                OptionDB optionDB = optionRepository.findById(submittedOptionDTO.getId()).orElseThrow(() -> new RuntimeException("Option not found"));
                newOption.setOptionDB(optionDB);
                menuOptionRepository.save(newOption);
            } else if (menuOptionDB != null && !submittedOptionDTO.isSelected()) {
                menuOptionRepository.delete(menuOptionDB);
            }
        }

        return menuDTO;
    }
}