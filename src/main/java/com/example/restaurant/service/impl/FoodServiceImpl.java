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
                    optionDTO.setSelected(false);
                    return optionDTO;
                })
                .collect(Collectors.toList());

        // Set the options to the MenuDTO
        menuDTO.setOptions(optionDTOs);

        return menuDTO;
    }

    @Override
    public List<OptionDTO> getMenuOptionsDetailed(Long menuId) {
        // Fetch menu options for the given menuId
        List<MenuOptionDB> menuOptions = menuOptionRepository.findByMenuId(menuId);

        // Map MenuOptionDB to OptionDTO
        return menuOptions.stream().map(menuOption -> {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(menuOption.getOptionId()); // Set option ID
            optionDTO.setName(menuOption.getOptionDB().getName()); // Set option name
            optionDTO.setOptionGroupId(menuOption.getOptionDB().getOptionGroupId()); // Set group ID
            optionDTO.setSelected(true); // Mark as selected
            return optionDTO;
        }).collect(Collectors.toList());
    }



    @Override
    @Transactional
    public MenuDTO handleMenuOptions(MenuDTO menuDTO) {
        // Validate that the menu exists
        MenuDB menuDB = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found for ID: " + menuDTO.getId()));

        // Handle adding new options
        if (menuDTO.getOptions() != null && !menuDTO.getOptions().isEmpty()) {
            for (OptionDTO optionDTO : menuDTO.getOptions()) {
                OptionDB optionDB = optionRepository.findById(optionDTO.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Option not found for ID: " + optionDTO.getId()));

                List<MenuOptionDB> menuOptions = menuOptionRepository.findByMenuIdAndOptionId(menuDTO.getId(), optionDTO.getId());
                if (menuOptions.isEmpty()) {
                    MenuOptionDB menuOptionDB = new MenuOptionDB();
                    menuOptionDB.setMenuDB(menuDB);
                    menuOptionDB.setOptionDB(optionDB);
                    menuOptionRepository.save(menuOptionDB);
                }
            }
        }

        // Handle removing options
        if (menuDTO.getRemovedOptions() != null && !menuDTO.getRemovedOptions().isEmpty()) {
            for (OptionDTO optionDTO : menuDTO.getRemovedOptions()) {
                menuOptionRepository.deleteByMenuIdAndOptionId(menuDTO.getId(), optionDTO.getId());
            }
        }

        // Fetch updated list of options for the menu
        List<MenuOptionDB> updatedMenuOptions = menuOptionRepository.findByMenuId(menuDTO.getId());

        List<OptionDTO> updatedOptions = updatedMenuOptions.stream()
                .map(mo -> {
                    OptionDTO dto = new OptionDTO();
                    dto.setId(mo.getOptionDB().getId());
                    dto.setName(mo.getOptionDB().getName());
                    return dto;
                })
                .collect(Collectors.toList());

        // Build the response DTO
        MenuDTO responseDTO = new MenuDTO();
        responseDTO.setId(menuDB.getId());
        responseDTO.setName(menuDB.getName());
        responseDTO.setDescription(menuDB.getDescription());
        responseDTO.setBasePrice(menuDB.getBasePrice());
        responseDTO.setOptions(updatedOptions);

        return responseDTO; // Spring Boot converts this into JSON
    }


}
