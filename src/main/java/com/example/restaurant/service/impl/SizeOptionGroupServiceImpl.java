package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeOptionGroupDTO;
import com.example.restaurant.entity.OptionGroupDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.entity.SizeGroupOptionGroupDB;
import com.example.restaurant.repository.OptionGroupRepository;
import com.example.restaurant.repository.SizeGroupOptionGroupRepository;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.service.SizeOptionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SizeOptionGroupServiceImpl implements SizeOptionGroupService {

    @Autowired
    private SizeGroupOptionGroupRepository sizeGroupOptionGroupRepository;

    @Autowired
    private SizeGroupRepository sizeGroupRepository;

    @Autowired
    private OptionGroupRepository optionGroupRepository;

    @Override
    public void linkSizeGroupAndOptionGroup(SizeOptionGroupDTO request) {
        Long sizeGroupId = request.getSizeGroupId();
        Long optionGroupId = request.getOptionGroupId();
        SizeGroupDB sizeGroupDB = sizeGroupRepository.findById(sizeGroupId).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        OptionGroupDB optionGroupDB = optionGroupRepository.findById(optionGroupId).orElseThrow(() -> new RuntimeException("OptionGroup not found"));

        SizeGroupOptionGroupDB sizeGroupOptionGroupDB = new SizeGroupOptionGroupDB();
        sizeGroupOptionGroupDB.setSizeGroupDB(sizeGroupDB);
        sizeGroupOptionGroupDB.setOptionGroupDB(optionGroupDB);

        sizeGroupOptionGroupRepository.save(sizeGroupOptionGroupDB);
    }

    @Override
    public void unlinkSizeGroupAndOptionGroup(Long sizeGroupOptionGroupId) {
        sizeGroupOptionGroupRepository.deleteById(sizeGroupOptionGroupId);
    }

}
