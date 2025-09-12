package com.example.restaurant.controller;

import com.example.restaurant.dto.ItemDTO;
import com.example.restaurant.entity.Item;
import com.example.restaurant.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * This getALl items is already fetched using /api/menu in MenuController
     */
    @GetMapping
    public ResponseEntity<List<Item>> getItems() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.findById(id);
//        return product.map(value -> ResponseEntity.ok(itemService.getProductResponse(value))).orElseGet(() -> ResponseEntity.notFound().build());
        if(item.isPresent()){
            return new ResponseEntity<>(item, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody ItemDTO itemDto) {
        try{
            Item item = itemService.createItem(itemDto);
            return new ResponseEntity<>(item, HttpStatus.CREATED);      // paxi uri location haalxu
        }catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Invalid input provided", HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<>("An error occurred while updating the item", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDto) {
        try {
            Optional<Item> existingItem = itemService.findById(id);
            if (existingItem.isPresent()) {
                Item updatedItem = itemService.updateItem(id, itemDto);
                return new ResponseEntity<>(updatedItem, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>("Invalid input provided", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while updating the item", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        boolean isDeleted = itemService.deleteById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Item not found", HttpStatus.NOT_FOUND);
    }

}