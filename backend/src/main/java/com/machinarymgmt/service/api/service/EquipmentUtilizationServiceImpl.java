package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.EquipmentUtilizationRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EquipmentUtilizationDto;
import com.machinarymgmt.service.api.mapper.EquipmentUtilizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentUtilizationServiceImpl implements EquipmentUtilizationService {
    
    private final EquipmentUtilizationRepository utilizationRepository;
    private final EquipmentUtilizationMapper utilizationMapper;
    
    @Override
    public List<EquipmentUtilization> findAll() {
        return utilizationRepository.findAll();
    }
    
    @Override
    public Page<EquipmentUtilization> findAll(Pageable pageable) {
        return utilizationRepository.findAll(pageable);
    }
    
    @Override
    public Optional<EquipmentUtilization> findById(Long id) {
        return utilizationRepository.findById(id);
    }
    
    @Override
    public List<EquipmentUtilization> findByEquipment(Equipment equipment) {
        return utilizationRepository.findByEquipment(equipment);
    }
    
    @Override
    public List<EquipmentUtilization> findByProject(Project project) {
        return utilizationRepository.findByProject(project);
    }
    
    @Override
    public List<EquipmentUtilization> findByMonthAndYear(Integer month, Integer year) {
        return utilizationRepository.findByMonthAndYear(month, year);
    }
    
    @Override
    public EquipmentUtilization save(EquipmentUtilization utilization) {
        return utilizationRepository.save(utilization);
    }
    
    @Override
    public void deleteById(Long id) {
        utilizationRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return utilizationRepository.existsById(id);
    }
    
    @Override
    public List<EquipmentUtilizationDto> findAllDto() {
        return utilizationMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<EquipmentUtilizationDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(utilizationMapper::toDto);
    }
    
    @Override
    public Optional<EquipmentUtilizationDto> findDtoById(Long id) {
        return findById(id).map(utilizationMapper::toDto);
    }
}

