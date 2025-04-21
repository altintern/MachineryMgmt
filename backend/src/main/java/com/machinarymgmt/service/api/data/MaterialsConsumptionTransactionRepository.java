package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MaterialsConsumptionTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaterialsConsumptionTransactionRepository extends JpaRepository<MaterialsConsumptionTransaction, Long> {
    List<MaterialsConsumptionTransaction> findByProject(Project project);
    List<MaterialsConsumptionTransaction> findByEquipment(Equipment equipment);
    List<MaterialsConsumptionTransaction> findByItem(Item item);
    Page<MaterialsConsumptionTransaction> findByIssueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}

