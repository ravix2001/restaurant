package com.example.restaurant.repository;

import com.example.restaurant.dto.SizeOptionDTO;
import com.example.restaurant.entity.SizeOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SizeOptionRepository extends JpaRepository<SizeOptionDB, Long> {

    List<SizeOptionDB> findBySizeGroupOptionGroupId(Long sizeGroupOptionGroupId);

//    List<SizeOptionDTO> findByOptionId(Long id);

    @Query("SELECT new com.example.restaurant.dto.SizeOptionDTO(so.sizeId, so.price) FROM SizeOptionDB so WHERE so.optionId = :optionId AND so.sizeGroupOptionGroupId = :sgogId")
    List<SizeOptionDTO> findSizePrices(@Param("optionId") Long optionId, @Param("sgogId") Long sizeGroupOptionGroupId);

    Optional<SizeOptionDB> findBySizeIdAndOptionIdAndSizeGroupOptionGroupId(Long sizeId, Long optionId, Long sizeGroupOptionGroupId);

//    @Query("SELECT new com.example.restaurant.dto.SizeOptionDTO(so.sizeId, so.price) FROM SizeOptionDB so WHERE so.optionId = :optionId")
//    List<SizeOptionDTO> findSizePricesByOptionId(@Param("optionId") Long optionId);


}

