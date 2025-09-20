package com.example.restaurant.repository;

import com.example.restaurant.dto.OptionDTO;
import com.example.restaurant.entity.OptionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionRepository extends JpaRepository<OptionDB, Long> {

    List<OptionDB> findByOptionGroupId(Long optionGroupId);

    void deleteByOptionGroupId(Long optionGroupId);

    @Query("SELECT new com.example.restaurant.dto.OptionDTO(o.id, o.name) FROM OptionDB o WHERE o.optionGroupId = :optionGroupId")
    List<OptionDTO> findOptionsByOptionGroupId(@Param("optionGroupId") Long optionGroupId);
}
