package com.example.restaurant.repository;

import com.example.restaurant.entity.SizeOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeOptionRepository extends JpaRepository<SizeOptionDB, Long> {

    List<SizeOptionDB> findBySizeGroupOptionGroupId(Long sizeGroupOptionGroupId);

    SizeOptionDB findBySizeGroupOptionGroupIdAndOptionDBIdAndSizeDBId(Long id, Long id1, Long id2);

//    SizeOptionDB findBySizeGroupOptionGroupIdAndOptionDB_IdAndSizeDB_Id(
//            Long sizeGroupOptionGroupId,
//            Long optionDBId,
//            Long sizeDBId
//    );
}

