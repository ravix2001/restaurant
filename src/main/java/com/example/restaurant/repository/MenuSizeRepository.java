package com.example.restaurant.repository;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.MenuSizeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuSizeRepository extends JpaRepository<MenuSizeDB, Long> {

    List<MenuSizeDB> findByMenuId(Long menuId);

    @Query("SELECT new com.example.restaurant.dto.SizeDTO( s.id, s.name, ms.price) FROM MenuSizeDB ms JOIN ms.sizeDB s WHERE ms.menuId = :menuId")
    List<SizeDTO> findSizesWithPriceByMenuId(@Param("menuId") Long menuId);
}
