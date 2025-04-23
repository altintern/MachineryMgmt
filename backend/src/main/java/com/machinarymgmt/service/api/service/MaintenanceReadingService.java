package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import com.machinarymgmt.service.dto.MaintenanceReadingDto;

import java.util.List;
import java.util.Optional;

public interface MaintenanceReadingService {
    List<MaintenanceReading> findAll();
    Optional<MaintenanceReading> findById(Long id);
    Optional<MaintenanceReading> findByMaintenanceLogId(Long logId);
    MaintenanceReading save(MaintenanceReading maintenanceReading);
    void deleteById(Long id);
    boolean existsById(Long id);
    void delete(MaintenanceReading maintenanceReading);
    
    Optional<MaintenanceReadingDto> findDtoByMaintenanceLogId(Long logId);
}