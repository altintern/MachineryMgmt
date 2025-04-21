package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.OvertimeReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OvertimeReportRepository extends JpaRepository<OvertimeReport, Long> {
    List<OvertimeReport> findByEmployee(Employee employee);
    List<OvertimeReport> findByDate(LocalDate date);
    Page<OvertimeReport> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}

