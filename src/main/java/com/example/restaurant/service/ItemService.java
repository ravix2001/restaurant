package com.example.restaurant.service;

import com.example.restaurant.dto.ItemDTO;
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


//            item.setName(updatedItem.getName());
//            item.setDescription(updatedItem.getDescription());
//            item.setPrice(updatedItem.getPrice());
//            item.setCategory(updatedItem.getCategory());
//            item.setMenu(updatedItem.getMenu());
//            return itemRepository.save(item);

    @Transactional
    public Item createItem(ItemDTO itemDto) {
        try {
            Item newItem = new Item();
            newItem.setName(itemDto.getName());
            newItem.setDescription(itemDto.getDescription());
            newItem.setPrice(itemDto.getPrice());
            Item item = itemRepository.save(newItem);
            return item;
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while creating an itemDto");
        }
    }

    @Transactional
    public Item updateItem(Long id, ItemDTO itemDto) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isPresent()) {
                item.get().setName(itemDto.getName());
                item.get().setDescription(itemDto.getDescription());
                item.get().setQuantity(itemDto.getQuantity());
                item.get().setPrice(itemDto.getPrice());
                Item updatedItem = itemRepository.save(item.get());
                return updatedItem;
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
