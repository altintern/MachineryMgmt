package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.StockStatement;
import com.machinarymgmt.service.dto.StockStatementDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StockStatementService {
    List<StockStatement> findAll();
    Page<StockStatement> findAll(Pageable pageable);
    Optional<StockStatement> findById(Long id);
    List<StockStatement> findByProject(Project project);
    List<StockStatement> findByItem(Item item);
    List<StockStatement> findByEquipment(Equipment equipment);
    List<StockStatement> findByMonthAndYear(Integer month, Integer year);
    StockStatement save(StockStatement stockStatement);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<StockStatementDto> findAllDto();
    Page<StockStatementDto> findAllDto(Pageable pageable);
    Optional<StockStatementDto> findDtoById(Long id);
}

