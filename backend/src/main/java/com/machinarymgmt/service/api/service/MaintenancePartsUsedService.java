package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.dto.MaintenancePartUsedDto;

import java.util.List;
import java.util.Optional;

public interface MaintenancePartsUsedService {
    List<MaintenancePartsUsed> findAll();
    Optional<MaintenancePartsUsed> findById(Long id);
    List<MaintenancePartsUsed> findByMaintenanceLogId(Long logId);
    List<MaintenancePartsUsed> findByItemId(Long itemId);
    MaintenancePartsUsed save(MaintenancePartsUsed maintenancePartsUsed);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MaintenancePartUsedDto> findAllDto();
    Optional<MaintenancePartUsedDto> findDtoById(Long id);
    List<MaintenancePartUsedDto> findDtoByMaintenanceLogId(Long logId);
}