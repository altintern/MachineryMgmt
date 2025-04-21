package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.dto.EquipmentCategoryDto;

import java.util.List;
import java.util.Optional;

public interface EquipmentCategoryService {
    List<EquipmentCategory> findAll();
    Optional<EquipmentCategory> findById(Long id);
    Optional<EquipmentCategory> findByName(String name);
    EquipmentCategory save(EquipmentCategory category);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
    List<EquipmentCategoryDto> findAllDto();
    Optional<EquipmentCategoryDto> findDtoById(Long id);
}

