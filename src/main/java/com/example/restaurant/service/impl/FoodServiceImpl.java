package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private OptionGroupRepository optionGroupRepository;

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
    public String updateMenuAndSize(MenuDTO dto) {
        MenuDB existingMenu = menuRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("menu not found"));
        existingMenu.setName(dto.getName());
        existingMenu.setBasePrice(dto.getBasePrice());

        CategoryDB existingCategory = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingMenu.setCategory(existingCategory);

        SizeGroupDB existingSizeGroup = sizeGroupRepository.findById(dto.getSizeGroupId())
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        existingMenu.setSizeGroup(existingSizeGroup);

        menuRepository.save(existingMenu);

        List<MenuSizeDB> menuSizeDBList = menuSizeRepository.findByMenuId(existingMenu.getId());
        for (MenuSizeDTO menuSizeDTO : dto.getMenuSizes()) {
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

    //    @Override
//    public String createOptionGroupWithOptions(OptionGroupDTO optionGroupDTO) {
//
//        OptionGroupDB optionGroupDB = new OptionGroupDB();
//        optionGroupDB.setName(optionGroupDTO.getName());
//        OptionGroupDB savedOptionGroup = optionGroupRepository.save(optionGroupDB);
//
//        for (OptionDTO optionDTO : optionGroupDTO.getOptions()) {
//            OptionDB optionDB = new OptionDB();
//            optionDB.setName(optionDTO.getName());
//            optionDB.setOptionGroupDB(savedOptionGroup);
//            optionRepository.save(optionDB);
//        }
//        return "Success";
//    }
//
//    @Override
//    public String updateOptionGroupWithOptions(OptionGroupDTO optionGroupDTO) {
//        OptionGroupDB optionGroupDBList = optionGroupRepository.findById(optionGroupDTO.getId())
//                .orElseThrow(() -> new RuntimeException("Option group not found"));
//        optionGroupDBList.setName(optionGroupDTO.getName());
//        OptionGroupDB savedOptionGroup = optionGroupRepository.save(optionGroupDBList);
//
//        List<OptionDB> optionDBList = optionRepository.findByOptionGroupId(optionGroupDBList.getId());
//        for (OptionDTO optionDTO : optionGroupDTO.getOptions()) {
//            for (OptionDB optionDB : optionDBList) {
//                if (optionDTO.getId().equals(optionDB.getId())) {
//                    optionDB.setName(optionDTO.getName());
//                    optionDB.setOptionGroupDB(savedOptionGroup);
//                    optionRepository.save(optionDB);
//                }
//            }
//        }
//        return "Success";
//    }
}
