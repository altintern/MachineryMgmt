package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.OvertimeReportRepository;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.OvertimeReport;
import com.machinarymgmt.service.dto.OvertimeReportDto;
import com.machinarymgmt.service.api.mapper.OvertimeReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OvertimeReportServiceImpl implements OvertimeReportService {
    
    private final OvertimeReportRepository overtimeReportRepository;
    private final OvertimeReportMapper overtimeReportMapper;
    
    @Override
    public List<OvertimeReport> findAll() {
        return overtimeReportRepository.findAll();
    }
    
    @Override
    public Page<OvertimeReport> findAll(Pageable pageable) {
        return overtimeReportRepository.findAll(pageable);
    }
    
    @Override
    public Optional<OvertimeReport> findById(Long id) {
        return overtimeReportRepository.findById(id);
    }
    
    @Override
    public List<OvertimeReport> findByEmployee(Employee employee) {
        return overtimeReportRepository.findByEmployee(employee);
    }
    
    @Override
    public List<OvertimeReport> findByDate(LocalDate date) {
        return overtimeReportRepository.findByDate(date);
    }
    
    @Override
    public Page<OvertimeReport> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return overtimeReportRepository.findByDateBetween(startDate, endDate, pageable);
    }
    
    @Override
    public OvertimeReport save(OvertimeReport overtimeReport) {
        return overtimeReportRepository.save(overtimeReport);
    }
    
    @Override
    public void deleteById(Long id) {
        overtimeReportRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return overtimeReportRepository.existsById(id);
    }
    
    @Override
    public List<OvertimeReportDto> findAllDto() {
        return overtimeReportMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<OvertimeReportDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(overtimeReportMapper::toDto);
    }
    
    @Override
    public Optional<OvertimeReportDto> findDtoById(Long id) {
        return findById(id).map(overtimeReportMapper::toDto);
    }
}

