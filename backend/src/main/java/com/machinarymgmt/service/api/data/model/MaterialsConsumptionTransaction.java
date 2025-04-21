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
@Table(name = "materials_consumption_transaction")
public class MaterialsConsumptionTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "materials_consumption_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;
    
    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;
    
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "cost_per_unit", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPerUnit;
    
    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;
    
    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

