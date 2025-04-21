package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MachineryMaintenanceLogRepository extends JpaRepository<MachineryMaintenanceLog, Long> {
    List<MachineryMaintenanceLog> findByEquipment(Equipment equipment);
    List<MachineryMaintenanceLog> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Page<MachineryMaintenanceLog> findByEquipmentAndDateBetween(Equipment equipment, LocalDate startDate, LocalDate endDate, Pageable pageable);
    boolean existsByEquipmentAndDate(Equipment equipment, LocalDate date);
}

