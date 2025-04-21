package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.MaintenanceReadingRepository;
import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import com.machinarymgmt.service.dto.MaintenanceReadingDto;
import com.machinarymgmt.service.api.mapper.MaintenanceReadingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceReadingServiceImpl implements MaintenanceReadingService {
    
    private final MaintenanceReadingRepository maintenanceReadingRepository;
    private final MaintenanceReadingMapper maintenanceReadingMapper;
    
    @Override
    public List<MaintenanceReading> findAll() {
        return maintenanceReadingRepository.findAll();
    }
    
    @Override
    public Optional<MaintenanceReading> findById(Long id) {
        return maintenanceReadingRepository.findById(id);
    }
    
    @Override
    public Optional<MaintenanceReading> findByMaintenanceLogId(Long logId) {
        return maintenanceReadingRepository.findByMaintenanceLogId(logId);
    }
    
    @Override
    public MaintenanceReading save(MaintenanceReading maintenanceReading) {
        return maintenanceReadingRepository.save(maintenanceReading);
    }
    
    @Override
    public void deleteById(Long id) {
        maintenanceReadingRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return maintenanceReadingRepository.existsById(id);
    }
    
    // @Override
    // public List<MaintenanceReadingDto> findAllDto() {
    //     return maintenanceReadingMapper.toDtoList(findAll());
    // }
    
    // @Override
    // public Optional<MaintenanceReadingDto> findDtoById(Long id) {
    //     return findById(id).map(maintenanceReadingMapper::toDto);
    // }
    
    @Override
    public Optional<MaintenanceReadingDto> findDtoByMaintenanceLogId(Long logId) {
        return findByMaintenanceLogId(logId).map(maintenanceReadingMapper::toDto);
    }
}