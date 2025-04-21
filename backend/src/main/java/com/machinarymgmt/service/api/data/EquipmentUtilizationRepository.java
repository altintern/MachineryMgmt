package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentUtilizationRepository extends JpaRepository<EquipmentUtilization, Long> {
    List<EquipmentUtilization> findByEquipment(Equipment equipment);
    List<EquipmentUtilization> findByProject(Project project);
    List<EquipmentUtilization> findByMonthAndYear(Integer month, Integer year);
}

