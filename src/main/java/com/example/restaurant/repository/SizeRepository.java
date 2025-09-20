package com.example.restaurant.repository;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SizeRepository extends JpaRepository<SizeDB, Long> {

    List<SizeDB> findBySizeGroupId(Long sizeGroupId);

    void deleteBySizeGroupId(Long sizeGroupId);

}
