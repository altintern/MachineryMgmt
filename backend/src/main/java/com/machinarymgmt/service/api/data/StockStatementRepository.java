package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.StockStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockStatementRepository extends JpaRepository<StockStatement, Long> {
    List<StockStatement> findByProject(Project project);
    List<StockStatement> findByItem(Item item);
    List<StockStatement> findByEquipment(Equipment equipment);
    List<StockStatement> findByMonthAndYear(Integer month, Integer year);
    boolean existsByProjectAndItemAndEquipment(Project project, Item item, Equipment equipment);
}

