package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.OvertimeReport;
import com.machinarymgmt.service.dto.OvertimeReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OvertimeReportService {
    List<OvertimeReport> findAll();
    Page<OvertimeReport> findAll(Pageable pageable);
    Optional<OvertimeReport> findById(Long id);
    List<OvertimeReport> findByEmployee(Employee employee);
    List<OvertimeReport> findByDate(LocalDate date);
    Page<OvertimeReport> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    OvertimeReport save(OvertimeReport overtimeReport);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<OvertimeReportDto> findAllDto();
    Page<OvertimeReportDto> findAllDto(Pageable pageable);
    Optional<OvertimeReportDto> findDtoById(Long id);
}

