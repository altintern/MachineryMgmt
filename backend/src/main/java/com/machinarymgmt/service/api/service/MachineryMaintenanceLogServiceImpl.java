package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.MachineryMaintenanceLogRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineryMaintenanceLogServiceImpl implements MachineryMaintenanceLogService {
    
    private final MachineryMaintenanceLogRepository maintenanceLogRepository;
    
    @Override
    public List<MachineryMaintenanceLog> findAll() {
        return maintenanceLogRepository.findAll();
    }
    
    @Override
    public Page<MachineryMaintenanceLog> findAll(Pageable pageable) {
        return maintenanceLogRepository.findAll(pageable);
    }
    
    @Override
    public Optional<MachineryMaintenanceLog> findById(Long id) {
        return maintenanceLogRepository.findById(id);
    }
    
    @Override
    public List<MachineryMaintenanceLog> findByEquipment(Equipment equipment) {
        return maintenanceLogRepository.findByEquipment(equipment);
    }
    
    @Override
    public List<MachineryMaintenanceLog> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return maintenanceLogRepository.findByDateBetween(startDate, endDate);
    }
    
    @Override
    public Page<MachineryMaintenanceLog> findByEquipmentAndDateBetween(Equipment equipment, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return maintenanceLogRepository.findByEquipmentAndDateBetween(equipment, startDate, endDate, pageable);
    }
    
    @Override
    public MachineryMaintenanceLog save(MachineryMaintenanceLog log) {
        return maintenanceLogRepository.save(log);
    }
    
    @Override
    public void deleteById(Long id) {
        maintenanceLogRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return maintenanceLogRepository.existsById(id);
    }
}

