package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.PettyCashTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PettyCashTransactionRepository extends JpaRepository<PettyCashTransaction, Long> {
    List<PettyCashTransaction> findByProject(Project project);
    List<PettyCashTransaction> findByEquipment(Equipment equipment);
    List<PettyCashTransaction> findByItem(Item item);
    Page<PettyCashTransaction> findByReportDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}

