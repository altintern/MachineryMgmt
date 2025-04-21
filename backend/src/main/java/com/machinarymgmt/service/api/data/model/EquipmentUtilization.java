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
@Table(name = "Equipment_Utilization")
public class EquipmentUtilization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UtilizationID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "EquipmentID", nullable = false)
    private Equipment equipment;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;
    
    @Column(name = "Month", nullable = false)
    private Integer month;
    
    @Column(name = "Year", nullable = false)
    private Integer year;
    
    @Column(name = "Targeted_Hours", precision = 10, scale = 2)
    private BigDecimal targetedHours;
    
    @Column(name = "Starting_Hours_Kms", precision = 10, scale = 2)
    private BigDecimal startingHoursKms;
    
    @Column(name = "Closing_Hours_Kms", precision = 10, scale = 2)
    private BigDecimal closingHoursKms;
    
    @Column(name = "Breakdown_Hours", precision = 10, scale = 2)
    private BigDecimal breakdownHours;
    
    @Column(name = "Diesel_Consumed_Ltrs", precision = 10, scale = 2)
    private BigDecimal dieselConsumedLtrs;
    
    @Column(name = "Avg_Fuel_Consumption", precision = 10, scale = 2)
    private BigDecimal avgFuelConsumption;
    
    @Column(name = "Availability_Hours", precision = 10, scale = 2)
    private BigDecimal availabilityHours;
    
    @Column(name = "Utilization_Percentage", precision = 5, scale = 2)
    private BigDecimal utilizationPercentage;
    
    @Column(name = "Remarks", columnDefinition = "TEXT")
    private String remarks;
}

