package com.example.restaurant.repository;

import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.SizeGroupOptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeGroupOptionGroupRepository extends JpaRepository<SizeGroupOptionGroupDB, Long> {

    List<SizeGroupOptionGroupDB> findBySizeGroupId(Long sizeGroupId);

    @Query("SELECT new com.example.restaurant.dto.OptionGroupDTO(og.id, og.name) FROM SizeGroupOptionGroupDB sgog JOIN sgog.optionGroupDB og WHERE sgog.sizeGroupId = :sizeGroupId")
    List<OptionGroupDTO> findOptionGroupsBySizeGroupId(@Param("sizeGroupId") Long sizeGroupId);
}
