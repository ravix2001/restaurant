package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuSizeDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuSizeRepository extends JpaRepository<MenuSizeDB, Long> {

    Optional<MenuSizeDB> findByItemIdAndSizeId(Long itemId, Long sizeId);
    List<MenuSizeDB> findByItemId(Long itemId);

}
