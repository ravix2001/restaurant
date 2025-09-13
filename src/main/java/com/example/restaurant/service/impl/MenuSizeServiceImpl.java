package com.example.restaurant.service.impl;

import com.example.restaurant.dto.MenuSizeDTO;
import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.entity.MenuSizeDB;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.mapper.ItemSizeMapper;
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

    private final MenuRepository itemRepo;
    private final SizeRepository sizeRepo;
    private final MenuSizeRepository itemSizeRepo;
    private final ItemSizeMapper mapper;

    public MenuSizeServiceImpl(MenuRepository itemRepo, SizeRepository sizeRepo, MenuSizeRepository itemSizeRepo, ItemSizeMapper mapper) {
        this.itemRepo = itemRepo;
        this.sizeRepo = sizeRepo;
        this.itemSizeRepo = itemSizeRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public MenuSizeDTO setPrice(Long itemId, MenuSizeDTO dto) {
        MenuDB item = itemRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        SizeDB sizeDB = sizeRepo.findById(dto.getSizeId()).orElseThrow(() -> new RuntimeException("Size not found"));

        // Optional business rule: ensure size belongs to item's category's size groups:
        // if (! size.getSizeGroup().getCategory().getId().equals(item.getCategory().getId())) throw new RuntimeException(...);

        MenuSizeDB is = itemSizeRepo.findByItemIdAndSizeId(itemId, dto.getSizeId()).orElseGet(() -> {
            MenuSizeDB n = new MenuSizeDB();
            n.setItem(item);
            n.setSizeDB(sizeDB);
            return n;
        });
        is.setPrice(dto.getPrice());
        MenuSizeDB saved = itemSizeRepo.save(is);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void removePrice(Long itemId, Long sizeId) {
        itemSizeRepo.findByItemIdAndSizeId(itemId, sizeId).ifPresent(itemSizeRepo::delete);
    }

    @Override
    public List<MenuSizeDTO> listForItem(Long itemId) {
        return itemSizeRepo.findByItemId(itemId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }
}
