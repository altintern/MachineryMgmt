package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Stock_Statement")
public class StockStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StockID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "ItemID", nullable = false)
    private Item item;
    
    @ManyToOne
    @JoinColumn(name = "EquipmentID", nullable = false)
    private Equipment equipment;
    
    @Column(name = "Month", nullable = false)
    private Integer month;
    
    @Column(name = "Year", nullable = false)
    private Integer year;
    
    @Column(name = "Balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;
    
    @Column(name = "Landed_Rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal landedRate;
    
    @Column(name = "Landed_Value", nullable = false, precision = 10, scale = 2)
    private BigDecimal landedValue;
    
    @Column(name = "Last_Receipt_On")
    private LocalDate lastReceiptOn;
    
    @Column(name = "Last_Issue_On")
    private LocalDate lastIssueOn;
}

