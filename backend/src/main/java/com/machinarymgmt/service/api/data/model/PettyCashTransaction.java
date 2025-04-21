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
@Table(name = "petty_cash_transaction")
public class PettyCashTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petty_cash_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;
    
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rate;
    
    @Column(name = "cumulative_total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal cumulativeTotalAmount;
    
    @Column(name = "amount_spent", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountSpent;
    
    @Column(name = "purpose_justification", columnDefinition = "TEXT")
    private String purposeJustification;
    
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
}

