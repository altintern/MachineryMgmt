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
@Table(name = "Maintenance_Readings")
public class MaintenanceReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReadingID")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "Log_ID", nullable = false)
    private MachineryMaintenanceLog maintenanceLog;
    
    @Column(name = "Oil_Pressure", precision = 5, scale = 2)
    private BigDecimal oilPressure;
    
    @Column(name = "Engine_Temperature", precision = 5, scale = 2)
    private BigDecimal engineTemperature;
    
    @Column(name = "Air_Pressure", precision = 5, scale = 2)
    private BigDecimal airPressure;
    
    @Column(name = "Hydraulic_Temperature", precision = 5, scale = 2)
    private BigDecimal hydraulicTemperature;
    
    @Column(name = "HSD_Used", precision = 5, scale = 2)
    private BigDecimal hsdUsed;
    
    @Column(name = "Engine_Oil", precision = 5, scale = 2)
    private BigDecimal engineOil;
    
    @Column(name = "Hydraulic_Oil", precision = 5, scale = 2)
    private BigDecimal hydraulicOil;
    
    @Column(name = "Gear_Oil", precision = 5, scale = 2)
    private BigDecimal gearOil;
    
    @Column(name = "Grease_Used", precision = 5, scale = 2)
    private BigDecimal greaseUsed;
}

