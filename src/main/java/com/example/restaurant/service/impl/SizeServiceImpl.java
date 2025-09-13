package com.example.restaurant.service.impl;

import com.example.restaurant.dto.SizeDTO;
import com.example.restaurant.entity.SizeDB;
import com.example.restaurant.entity.SizeGroupDB;
import com.example.restaurant.mapper.SizeMapper;
import com.example.restaurant.repository.SizeGroupRepository;
import com.example.restaurant.repository.SizeRepository;
import com.example.restaurant.service.SizeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository repo;
    private final SizeGroupRepository sizeGroupRepo;
    private final SizeMapper mapper;

    public SizeServiceImpl(SizeRepository repo, SizeGroupRepository sizeGroupRepo, SizeMapper mapper) {
        this.repo = repo;
        this.sizeGroupRepo = sizeGroupRepo;
        this.mapper = mapper;
    }

    @Override
    public SizeDTO create(Long sizeGroupId, SizeDTO dto) {
        SizeGroupDB sg = sizeGroupRepo.findById(sizeGroupId).orElseThrow(() -> new RuntimeException("SizeGroup not found"));
        SizeDB s = mapper.toEntity(dto);
        s.setSizeGroupDB(sg);
        SizeDB saved = repo.save(s);
        return mapper.toDTO(saved);
    }

    @Override
    public SizeDTO update(Long id, SizeDTO dto) {
        SizeDB s = repo.findById(id).orElseThrow(() -> new RuntimeException("Size not found"));
        s.setName(dto.getName());
        return mapper.toDTO(repo.save(s));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<SizeDTO> findBySizeGroup(Long sizeGroupId) {
        return repo.findBySizeGroupId(sizeGroupId).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SizeDTO getById(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElseThrow(() -> new RuntimeException("Size not found"));
    }
}
