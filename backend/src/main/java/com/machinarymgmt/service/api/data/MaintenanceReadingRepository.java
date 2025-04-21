package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceReadingRepository extends JpaRepository<MaintenanceReading, Long> {
    Optional<MaintenanceReading> findByMaintenanceLogId(Long logId);
    //Optional<MaintenanceReading> findByEquipment_Id(Long equipmentId);
    Optional<MaintenanceReading> findByMaintenanceLog_Equipment_Id(Long equipmentId);
}