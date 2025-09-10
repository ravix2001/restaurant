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
//        return items.stream()
//                .map(this::mapToItemDto).toList();
    }

    public Optional<Item> findById(Long id){
        return itemRepository.findById(id);
    }

    public Optional<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void createItem(Item item) {
        try {
            Item newItem = new Item();
            newItem.setName(item.getName());
            newItem.setDescription(item.getDescription());
            newItem.setQuantity(item.getQuantity());
            newItem.setPrice(item.getPrice());
            itemRepository.save(item);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while creating an item");
        }
    }

    @Transactional
    public void updateItem(Long id, Item newItem) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isPresent()) {
                item.get().setName(newItem.getName());
                item.get().setDescription(newItem.getDescription());
                item.get().setQuantity(newItem.getQuantity());
                item.get().setPrice(newItem.getPrice());
                itemRepository.save(item.get());
            } else {
                throw new RuntimeException("Item not found");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while updating an item");
        }
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
