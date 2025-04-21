package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.MaterialsConsumptionTransactionRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MaterialsConsumptionTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.mapper.MaterialsConsumptionTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaterialsConsumptionTransactionServiceImpl implements MaterialsConsumptionTransactionService {

    private final MaterialsConsumptionTransactionRepository transactionRepository;
    private final MaterialsConsumptionTransactionMapper transactionMapper;

    @Override
    public List<MaterialsConsumptionTransaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Page<MaterialsConsumptionTransaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public Optional<MaterialsConsumptionTransaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<MaterialsConsumptionTransaction> findByProject(Project project) {
        return transactionRepository.findByProject(project);
    }

    @Override
    public List<MaterialsConsumptionTransaction> findByEquipment(Equipment equipment) {
        return transactionRepository.findByEquipment(equipment);
    }

    @Override
    public List<MaterialsConsumptionTransaction> findByItem(Item item) {
        return transactionRepository.findByItem(item);
    }

    @Override
    public Page<MaterialsConsumptionTransaction> findByIssueDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return transactionRepository.findByIssueDateBetween(startDate, endDate, pageable);
    }

    @Override
    public MaterialsConsumptionTransaction save(MaterialsConsumptionTransaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return transactionRepository.existsById(id);
    }
}

