package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.PettyCashTransactionRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.PettyCashTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.PettyCashTransactionDto;
import com.machinarymgmt.service.api.mapper.PettyCashTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PettyCashTransactionServiceImpl implements PettyCashTransactionService {
    
    private final PettyCashTransactionRepository transactionRepository;
    private final PettyCashTransactionMapper transactionMapper;
    
    @Override
    public List<PettyCashTransaction> findAll() {
        return transactionRepository.findAll();
    }
    
    @Override
    public Page<PettyCashTransaction> findAll(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }
    
    @Override
    public Optional<PettyCashTransaction> findById(Long id) {
        return transactionRepository.findById(id);
    }
    
    @Override
    public List<PettyCashTransaction> findByProject(Project project) {
        return transactionRepository.findByProject(project);
    }
    
    @Override
    public List<PettyCashTransaction> findByEquipment(Equipment equipment) {
        return transactionRepository.findByEquipment(equipment);
    }
    
    @Override
    public List<PettyCashTransaction> findByItem(Item item) {
        return transactionRepository.findByItem(item);
    }
    
    @Override
    public Page<PettyCashTransaction> findByReportDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return transactionRepository.findByReportDateBetween(startDate, endDate, pageable);
    }
    
    @Override
    public PettyCashTransaction save(PettyCashTransaction transaction) {
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
    
    @Override
    public List<PettyCashTransactionDto> findAllDto() {
        return transactionMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<PettyCashTransactionDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(transactionMapper::toDto);
    }
    
    @Override
    public Optional<PettyCashTransactionDto> findDtoById(Long id) {
        return findById(id).map(transactionMapper::toDto);
    }
}

