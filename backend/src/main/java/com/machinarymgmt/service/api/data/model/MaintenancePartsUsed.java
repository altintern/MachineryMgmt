package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Maintenance_Parts_Used")
public class MaintenancePartsUsed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PartUsageID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "Log_ID", nullable = false)
    private MachineryMaintenanceLog maintenanceLog;
    
    @ManyToOne
    @JoinColumn(name = "ItemID", nullable = false)
    private Item item;
    
    @Column(name = "Quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;
}

