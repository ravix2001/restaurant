package com.example.restaurant.repository;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.entity.MenuOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOptionDB, Long> {

    List<MenuOptionDB> findByMenuId(Long menuId);

    void deleteByMenuIdAndOptionId(Long menuId, Long optionId);

    MenuOptionDB findByOptionId(Long optionId);

//    List<OptionDTO> findOptionsByMenuId(Long menuId);

    @Query("SELECT new com.example.restaurant.dto.OptionDTO(o.id, o.name) FROM MenuOptionDB mo JOIN mo.optionDB o WHERE mo.menuId = :menuId")
    List<OptionDTO> findMenuOptionsByMenuId(@Param("menuId") Long menuId);

}
