package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EquipmentUtilizationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EquipmentUtilizationService {
    List<EquipmentUtilization> findAll();
    Page<EquipmentUtilization> findAll(Pageable pageable);
    Optional<EquipmentUtilization> findById(Long id);
    List<EquipmentUtilization> findByEquipment(Equipment equipment);
    List<EquipmentUtilization> findByProject(Project project);
    List<EquipmentUtilization> findByMonthAndYear(Integer month, Integer year);
    EquipmentUtilization save(EquipmentUtilization utilization);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<EquipmentUtilizationDto> findAllDto();
    Page<EquipmentUtilizationDto> findAllDto(Pageable pageable);
    Optional<EquipmentUtilizationDto> findDtoById(Long id);
}

