package com.example.restaurant.service;

import com.example.restaurant.dto.SizeOptionGroupDTO;

public interface SizeOptionGroupService {

    void linkSizeGroupAndOptionGroup(SizeOptionGroupDTO request);
    void unlinkSizeGroupAndOptionGroup(Long sizeGroupOptionGroupId);

}
