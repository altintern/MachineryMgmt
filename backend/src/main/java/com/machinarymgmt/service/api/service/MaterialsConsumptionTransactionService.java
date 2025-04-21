package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MaterialsConsumptionTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.MaterialsConsumptionTransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MaterialsConsumptionTransactionService {
    List<MaterialsConsumptionTransaction> findAll();
    Page<MaterialsConsumptionTransaction> findAll(Pageable pageable);
    Optional<MaterialsConsumptionTransaction> findById(Long id);
    List<MaterialsConsumptionTransaction> findByProject(Project project);
    List<MaterialsConsumptionTransaction> findByEquipment(Equipment equipment);
    List<MaterialsConsumptionTransaction> findByItem(Item item);
    Page<MaterialsConsumptionTransaction> findByIssueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    MaterialsConsumptionTransaction save(MaterialsConsumptionTransaction transaction);
    void deleteById(Long id);
    boolean existsById(Long id);
}

