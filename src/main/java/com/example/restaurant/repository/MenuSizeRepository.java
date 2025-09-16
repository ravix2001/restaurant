package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuDB;
import com.example.restaurant.entity.MenuSizeDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuSizeRepository extends JpaRepository<MenuSizeDB, Long> {

    Optional<MenuSizeDB> findByMenuIdAndSizeId(Long menuId, Long sizeId);
    List<MenuSizeDB> findByMenuId(Long menuId);
    void deleteByMenuDB(MenuDB menuDB);
    List<MenuSizeDB> findByMenuDB(MenuDB menu);

}
