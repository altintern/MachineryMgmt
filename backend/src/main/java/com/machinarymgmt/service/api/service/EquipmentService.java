package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EquipmentService {
    List<Equipment> findAll();
    Page<Equipment> findAll(Pageable pageable);
    Optional<Equipment> findById(Long id);
    List<Equipment> findByProject(Project project);
    List<Equipment> findByCategory(EquipmentCategory category);
    List<Equipment> findByModel(Model model);
    Page<Equipment> findByNameContaining(String name, Pageable pageable);
    Optional<Equipment> findByAssetCode(String assetCode);
    Equipment save(Equipment equipment);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByAssetCode(String assetCode);
}

