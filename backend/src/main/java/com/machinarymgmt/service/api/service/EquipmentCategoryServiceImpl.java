package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.EquipmentCategoryRepository;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.dto.EquipmentCategoryDto;
import com.machinarymgmt.service.api.mapper.EquipmentCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentCategoryServiceImpl implements EquipmentCategoryService {
    
    private final EquipmentCategoryRepository categoryRepository;
    private final EquipmentCategoryMapper categoryMapper;
    
    @Override
    public List<EquipmentCategory> findAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Optional<EquipmentCategory> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    @Override
    public Optional<EquipmentCategory> findByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name));
    }
    
    @Override
    public EquipmentCategory save(EquipmentCategory category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
    
    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }
    
    @Override
    public List<EquipmentCategoryDto> findAllDto() {
        return categoryMapper.toDtoList(findAll());
    }
    
    @Override
    public Optional<EquipmentCategoryDto> findDtoById(Long id) {
        return findById(id).map(categoryMapper::toDto);
    }
}

