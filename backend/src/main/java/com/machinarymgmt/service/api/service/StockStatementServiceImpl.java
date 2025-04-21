package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.StockStatementRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.StockStatement;
import com.machinarymgmt.service.dto.StockStatementDto;
import com.machinarymgmt.service.api.mapper.StockStatementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockStatementServiceImpl implements StockStatementService {
    
    private final StockStatementRepository stockStatementRepository;
    private final StockStatementMapper stockStatementMapper;
    
    @Override
    public List<StockStatement> findAll() {
        return stockStatementRepository.findAll();
    }
    
    @Override
    public Page<StockStatement> findAll(Pageable pageable) {
        return stockStatementRepository.findAll(pageable);
    }
    
    @Override
    public Optional<StockStatement> findById(Long id) {
        return stockStatementRepository.findById(id);
    }
    
    @Override
    public List<StockStatement> findByProject(Project project) {
        return stockStatementRepository.findByProject(project);
    }
    
    @Override
    public List<StockStatement> findByItem(Item item) {
        return stockStatementRepository.findByItem(item);
    }
    
    @Override
    public List<StockStatement> findByEquipment(Equipment equipment) {
        return stockStatementRepository.findByEquipment(equipment);
    }
    
    @Override
    public List<StockStatement> findByMonthAndYear(Integer month, Integer year) {
        return stockStatementRepository.findByMonthAndYear(month, year);
    }
    
    @Override
    public StockStatement save(StockStatement stockStatement) {
        return stockStatementRepository.save(stockStatement);
    }
    
    @Override
    public void deleteById(Long id) {
        stockStatementRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return stockStatementRepository.existsById(id);
    }
    
    @Override
    public List<StockStatementDto> findAllDto() {
        return stockStatementMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<StockStatementDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(stockStatementMapper::toDto);
    }
    
    @Override
    public Optional<StockStatementDto> findDtoById(Long id) {
        return findById(id).map(stockStatementMapper::toDto);
    }
}

