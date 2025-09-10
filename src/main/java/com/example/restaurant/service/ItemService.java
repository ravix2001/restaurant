package com.example.restaurant.service;

import com.example.restaurant.dto.ItemDto;
import com.example.restaurant.entity.Item;
import com.example.restaurant.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
//        return products.stream()
//                .map(this::mapToProductResponse).toList();
    }

    public Optional<Item> findById(Long id){
        return itemRepository.findById(id);
    }

    public Optional<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Transactional
    public void saveProduct(Item item) {
        itemRepository.save(item);
    }

    public ItemDto update(ItemDto itemDto) {
        return null;
    }

    public boolean deleteById(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    public ItemDto getItemDto(Item item) {
//        return mapToItemDto(item);
//    }
//
//    private ItemDto mapToItemDto(Item item) {
//        ItemDto itemDto = new ItemDto();
//        itemDto.setId(item.getId());
//        itemDto.setName(item.getName());
//        itemDto.setDescription(item.getDescription());
//        itemDto.setQuantity(item.getQuantity());
//        itemDto.setPrice(item.getPrice());
//
//        return itemDto;
//    }



}
