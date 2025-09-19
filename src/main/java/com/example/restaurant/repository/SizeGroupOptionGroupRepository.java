package com.example.restaurant.repository;

import com.example.restaurant.entity.SizeGroupOptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeGroupOptionGroupRepository extends JpaRepository<SizeGroupOptionGroupDB, Long> {

    List<SizeGroupOptionGroupDB> findBySizeGroupId(Long sizeGroupId);
}
