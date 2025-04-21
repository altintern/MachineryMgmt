package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.PettyCashTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.PettyCashTransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PettyCashTransactionService {
    List<PettyCashTransaction> findAll();
    Page<PettyCashTransaction> findAll(Pageable pageable);
    Optional<PettyCashTransaction> findById(Long id);
    List<PettyCashTransaction> findByProject(Project project);
    List<PettyCashTransaction> findByEquipment(Equipment equipment);
    List<PettyCashTransaction> findByItem(Item item);
    Page<PettyCashTransaction> findByReportDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    PettyCashTransaction save(PettyCashTransaction transaction);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<PettyCashTransactionDto> findAllDto();
    Page<PettyCashTransactionDto> findAllDto(Pageable pageable);
    Optional<PettyCashTransactionDto> findDtoById(Long id);
}

