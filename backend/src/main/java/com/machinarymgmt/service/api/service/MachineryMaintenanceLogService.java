package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MachineryMaintenanceLogService {
    List<MachineryMaintenanceLog> findAll();
    Page<MachineryMaintenanceLog> findAll(Pageable pageable);
    Optional<MachineryMaintenanceLog> findById(Long id);
    List<MachineryMaintenanceLog> findByEquipment(Equipment equipment);
    List<MachineryMaintenanceLog> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Page<MachineryMaintenanceLog> findByEquipmentAndDateBetween(Equipment equipment, LocalDate startDate, LocalDate endDate, Pageable pageable);
    MachineryMaintenanceLog save(MachineryMaintenanceLog log);
    void deleteById(Long id);
    boolean existsById(Long id);
}

