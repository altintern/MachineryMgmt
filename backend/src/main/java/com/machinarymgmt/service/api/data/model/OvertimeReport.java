package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Overtime_Report")
public class OvertimeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Report_ID")
    private Long id;
    
    @Column(name = "Date", nullable = false)
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;
    
    @Column(name = "Present_Days", nullable = false)
    private Integer presentDays;
    
    @Column(name = "OT_Hours", nullable = false, precision = 5, scale = 2)
    private BigDecimal otHours;
    
    @Column(name = "Remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @Column(name = "Created_At")
    private LocalDateTime createdAt;
}

