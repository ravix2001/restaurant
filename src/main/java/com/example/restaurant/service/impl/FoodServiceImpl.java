package com.example.restaurant.service.impl;

import com.example.restaurant.dto.*;
import com.example.restaurant.entity.*;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

//    @Override
//    public SizeGroupDTO createSizeGroupAndSize(SizeGroupDTO sizeGroupDTO) {
//        SizeGroupDB sizeGroup = new SizeGroupDB();
//        sizeGroup.setName(sizeGroupDTO.getName());
//        sizeGroupRepository.save(sizeGroup);
//
//        List<SizeDTO> updatedSizes = new ArrayList<>();
//
//        for (SizeDTO sizeDTO : sizeGroupDTO.getSizes()) {
//            SizeDB size = new SizeDB();
//            size.setName(sizeDTO.getName());
//
//            size.setSizeGroup(sizeGroup);
//
//            sizeRepository.save(size);
//
//            SizeDTO updatedSizeDTO = new SizeDTO();
//            updatedSizeDTO.setId(size.getId());
//            updatedSizeDTO.setName(size.getName());
//            updatedSizeDTO.setSizeGroupId(size.getSizeGroup().getId());
//
//            updatedSizes.add(updatedSizeDTO);
//        }
//
//        sizeGroupDTO.setSizes(updatedSizes);
//        sizeGroupDTO.setId(sizeGroup.getId());
//
//        return sizeGroupDTO;
//    }
//
//    @Override
//    public SizeGroupDTO updateSizeGroupAndSize(SizeGroupDTO sizeGroupDTO) {
//        Long sizeGroupId = sizeGroupDTO.getId();
//        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(sizeGroupId)
//                .orElseThrow(() -> new RuntimeException("Size group with id " + sizeGroupId + " does not exist"));
//
//        sizeGroupDB.setName(sizeGroupDTO.getName());
//
//        List<SizeDB> updatedSizes = new ArrayList<>();
//
//        for (SizeDTO sizeDTO : sizeGroupDTO.getSizes()) {
//            SizeDB existingSize = sizeRepository.findById(sizeDTO.getId())
//                    .orElseThrow(() -> new RuntimeException("Size with id " + sizeDTO.getId() + " does not exist"));
//
//            existingSize.setName(sizeDTO.getName());
//            existingSize.setSizeGroup(sizeGroupDB);
//
//            updatedSizes.add(existingSize);
//        }
//
//        sizeRepository.saveAll(updatedSizes);
//
//        sizeGroupDB.setSizes(updatedSizes);
//        sizeGroupRepository.save(sizeGroupDB);
//
//        SizeGroupDTO updatedSizeGroupDTO = new SizeGroupDTO();
//        updatedSizeGroupDTO.setId(sizeGroupId);
//        updatedSizeGroupDTO.setName(sizeGroupDTO.getName());
//
//        updatedSizeGroupDTO.setSizes(updatedSizes.stream()
//                .map(size -> new SizeDTO(size.getId(), size.getName(), size.getSizeGroup().getId()))
//                .collect(Collectors.toList()));
//
//        return updatedSizeGroupDTO;
//    }


    @Override
    public MenuDTO createMenuAndSize(MenuDTO menuDTO) {
        MenuDB menu = new MenuDB();
        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        menu.setBasePrice(menuDTO.getBasePrice());

        CategoryDB category = categoryRepository.findById(menuDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        menu.setCategory(category);

        menuRepository.save(menu);

        Long sizeGroupId = menuDTO.getSizeGroupId();

        SizeGroupDB sizeGroup = sizeGroupRepository.findById(sizeGroupId)
                .orElseThrow(() -> new RuntimeException("SizeGroup not found"));

        List<MenuSizeDB> menuSizeDBList = new ArrayList<>();
        for (SizeDB size : sizeGroup.getSizes()) {
            MenuSizeDB menuSizeDB = new MenuSizeDB();
            menuSizeDB.setPrice(menuDTO.getBasePrice());
            menuSizeDB.setMenuDB(menu);
            menuSizeDB.setSizeDB(size);

            menuSizeDBList.add(menuSizeDB);
        }

        menuSizeRepository.saveAll(menuSizeDBList);

        List<MenuSizeDTO> menuSizeDTOs = new ArrayList<>();
        for (MenuSizeDB menuSizeDB : menuSizeDBList) {
            MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
            menuSizeDTO.setId(menuSizeDB.getId());
            menuSizeDTO.setPrice(menuSizeDB.getPrice());
            menuSizeDTO.setMenuId(menuSizeDB.getMenuDB().getId());
            menuSizeDTO.setSizeId(menuSizeDB.getSizeDB().getId());
            menuSizeDTOs.add(menuSizeDTO);
        }

        menuDTO.setId(menu.getId());
        menuDTO.setSizeGroupId(sizeGroupId);
        menuDTO.setMenuSizes(menuSizeDTOs);

        return menuDTO;
    }

//    @Override
//    public MenuDTO updateMenuAndSize(MenuDTO menuDTO) {
//        Long menuId = menuDTO.getId();
//        MenuDB existingMenu = menuRepository.findById(menuId)
//                .orElseThrow(() -> new RuntimeException("Menu not found"));
//
//        existingMenu.setName(menuDTO.getName());
//        existingMenu.setBasePrice(menuDTO.getBasePrice());
//
//        CategoryDB category = categoryRepository.findById(menuDTO.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//        existingMenu.setCategory(category);
//
//        Long sizeGroupId = menuDTO.getSizeGroupId();
//
//        List<MenuSizeDB> updatedMenuSizeDBList = new ArrayList<>();
//
//        for (MenuSizeDTO menuSizeDTO : menuDTO.getMenuSizes()) {
//            MenuSizeDB existingMenuSize = menuSizeRepository.findById(menuSizeDTO.getId())
//                    .orElseThrow(() -> new RuntimeException("MenuSize not found for id " + menuSizeDTO.getId()));
//
//            existingMenuSize.setPrice(menuSizeDTO.getPrice());
//
//            SizeDB size = sizeRepository.findById(menuSizeDTO.getSizeId())
//                    .orElseThrow(() -> new RuntimeException("Size not found for id " + menuSizeDTO.getSizeId()));
//            existingMenuSize.setSizeDB(size);
//            existingMenuSize.setMenuDB(existingMenu);
//
//            updatedMenuSizeDBList.add(existingMenuSize);
//        }
//
//        menuSizeRepository.saveAll(updatedMenuSizeDBList);
//
//        menuRepository.save(existingMenu);
//
//        List<MenuSizeDTO> menuSizeDTOs = new ArrayList<>();
//        for (MenuSizeDB menuSizeDB : menuSizeDBList) {
//            MenuSizeDTO menuSizeDTO = new MenuSizeDTO();
//            menuSizeDTO.setId(menuSizeDB.getId());
//            menuSizeDTO.setPrice(menuSizeDB.getPrice());
//            menuSizeDTO.setMenuId(menuSizeDB.getMenuDB().getId());
//            menuSizeDTO.setSizeId(menuSizeDB.getSizeDB().getId());
//            menuSizeDTOs.add(menuSizeDTO);
//        }
//
//        menuDTO.setId(menu.getId());
//        menuDTO.setSizeGroupId(sizeGroupId);
//        menuDTO.setMenuSizes(menuSizeDTOs);
//
//        List<MenuSizeDTO> updatedMenuSizeDTOs = updatedMenuSizeDBList.stream()
//                .map(menuSizeDB -> new MenuSizeDTO(
//                        menuSizeDB.getId(),
//                        menuSizeDB.getPrice(),
//                        menuSizeDB.getMenuDB().getId(),
//                        menuSizeDB.getSizeDB().getId()))
//                .collect(Collectors.toList());
//
//        menuDTO.setName(existingMenu.getName());
//        menuDTO.setBasePrice(existingMenu.getBasePrice());
//        menuDTO.setCategoryId(existingMenu.getCategoryId());
//        menuDTO.setSizeGroupId(sizeGroupId);
//        menuDTO.setMenuSizes(updatedMenuSizeDTOs);
//
//        return menuDTO;
//    }

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
}
