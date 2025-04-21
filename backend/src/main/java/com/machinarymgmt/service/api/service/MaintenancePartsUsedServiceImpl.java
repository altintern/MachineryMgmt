package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.MaintenancePartsUsedRepository;
import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.dto.MaintenancePartUsedDto;
import com.machinarymgmt.service.api.mapper.MaintenancePartUsedMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenancePartsUsedServiceImpl implements MaintenancePartsUsedService {
    
    private final MaintenancePartsUsedRepository maintenancePartsUsedRepository;
    private final MaintenancePartUsedMapper maintenancePartsUsedMapper;
    
    @Override
    public List<MaintenancePartsUsed> findAll() {
        return maintenancePartsUsedRepository.findAll();
    }
    
    @Override
    public Optional<MaintenancePartsUsed> findById(Long id) {
        return maintenancePartsUsedRepository.findById(id);
    }
    
    @Override
    public List<MaintenancePartsUsed> findByMaintenanceLogId(Long logId) {
        return maintenancePartsUsedRepository.findByMaintenanceLogId(logId);
    }
    
    @Override
    public List<MaintenancePartsUsed> findByItemId(Long itemId) {
        return maintenancePartsUsedRepository.findByItemId(itemId);
    }
    
    @Override
    public MaintenancePartsUsed save(MaintenancePartsUsed maintenancePartsUsed) {
        return maintenancePartsUsedRepository.save(maintenancePartsUsed);
    }
    
    @Override
    public void deleteById(Long id) {
        maintenancePartsUsedRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return maintenancePartsUsedRepository.existsById(id);
    }
    
    @Override
    public List<MaintenancePartUsedDto> findAllDto() {
        return maintenancePartsUsedMapper.toDtoList(findAll());
    }
    
    @Override
    public Optional<MaintenancePartUsedDto> findDtoById(Long id) {
        return findById(id).map(maintenancePartsUsedMapper::toDto);
    }
    
    @Override
    public List<MaintenancePartUsedDto> findDtoByMaintenanceLogId(Long logId) {
        return maintenancePartsUsedMapper.toDtoList(findByMaintenanceLogId(logId));
    }
}