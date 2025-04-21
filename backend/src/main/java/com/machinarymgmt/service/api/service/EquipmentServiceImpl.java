package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.EquipmentRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.api.data.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    
    private final EquipmentRepository equipmentRepository;
    
    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }
    
    @Override
    public Page<Equipment> findAll(Pageable pageable) {
        return equipmentRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Equipment> findById(Long id) {
        return equipmentRepository.findById(id);
    }
    
    @Override
    public List<Equipment> findByProject(Project project) {
        return equipmentRepository.findByProject(project);
    }
    
    @Override
    public List<Equipment> findByCategory(EquipmentCategory category) {
        return equipmentRepository.findByCategory(category);
    }
    
    @Override
    public List<Equipment> findByModel(Model model) {
        return equipmentRepository.findByModel(model);
    }
    
    @Override
    public Page<Equipment> findByNameContaining(String name, Pageable pageable) {
        return equipmentRepository.findByNameContaining(name, pageable);
    }
    
    @Override
    public Optional<Equipment> findByAssetCode(String assetCode) {
        return Optional.ofNullable(equipmentRepository.findByAssetCode(assetCode));
    }
    
    @Override
    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }
    
    @Override
    public void deleteById(Long id) {
        equipmentRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return equipmentRepository.existsById(id);
    }
    
    @Override
    public boolean existsByAssetCode(String assetCode) {
        return equipmentRepository.existsByAssetCode(assetCode);
    }
}

