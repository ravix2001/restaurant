package com.example.restaurant.service.impl;

import com.example.restaurant.dto.MenuSizeDTO;
import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.entity.MenuSizeDB;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.repository.MenuRepository;
import com.example.restaurant.repository.MenuSizeRepository;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.MenuSizeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuSizeServiceImpl implements MenuSizeService {

    private final MenuRepository menuRepository;
    private final SizeRepository sizeRepository;
    private final MenuSizeRepository menuSizeRepository;

    public MenuSizeServiceImpl(MenuRepository menuRepository, SizeRepository sizeRepository, MenuSizeRepository menuSizeRepository) {
        this.menuRepository = menuRepository;
        this.sizeRepository = sizeRepository;
        this.menuSizeRepository = menuSizeRepository;
    }

    @Override
    @Transactional
    public MenuSizeDB setPrice(Long menuId, MenuSizeDTO menuSizeDTO) {
        MenuDB menuDB = menuRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));
        SizeDB sizeDB = sizeRepository.findById(menuSizeDTO.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));

        // Fetch if already present or create a new MenuSizeDB entity if not present
        MenuSizeDB menuSizeDB = menuSizeRepository.findByMenuIdAndSizeId(menuId, menuSizeDTO.getSizeId())
                .orElseGet(() -> {
                    MenuSizeDB newMenuSize = new MenuSizeDB();
                    newMenuSize.setMenuDB(menuDB);
                    newMenuSize.setSizeDB(sizeDB);
                    return newMenuSize;
                });

        // Set price and save
        menuSizeDB.setPrice(menuSizeDTO.getPrice());
        MenuSizeDB savedMenuSize = menuSizeRepository.save(menuSizeDB);

        return savedMenuSize;
    }

    @Override
    @Transactional
    public void removePrice(Long menuId, Long sizeId) {
        MenuSizeDB menuSizeDB = menuSizeRepository.findByMenuIdAndSizeId(menuId, sizeId).orElseThrow(() -> new RuntimeException("MenuSize not found for Menu ID: " + menuId + ", Size ID: " + sizeId));
        menuSizeRepository.delete(menuSizeDB);
    }

    @Override
    public List<MenuSizeDB> listForMenu(Long menuId) {
        return menuSizeRepository.findByMenuId(menuId);
    }
}
