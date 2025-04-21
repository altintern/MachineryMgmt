package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Machinery_Maintenance_Log")
public class MachineryMaintenanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_ID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "EquipmentID", nullable = false)
    private Equipment equipment;
    
    @Column(name = "Date", nullable = false)
    private LocalDate date;
    
    @Column(name = "Start_Reading", nullable = false)
    private Integer startReading;
    
    @Column(name = "Close_Reading", nullable = false)
    private Integer closeReading;
    
    @Column(name = "Service_Hours")
    private Integer serviceHours;
    
    @Column(name = "Service_Date")
    private LocalDate serviceDate;
    
    @Column(name = "Balance_For_Service")
    private Integer balanceForService;
    
    @Column(name = "Purpose_Activities", columnDefinition = "TEXT")
    private String purposeActivities;
    
    @Column(name = "Type_Of_Maintenance")
    private String typeOfMaintenance;
    
    @Column(name = "Breakdown_Synopsis", columnDefinition = "TEXT")
    private String breakdownSynopsis;
    
    @Column(name = "Operator_Name", nullable = false)
    private String operatorName;
    
    @Column(name = "Operator_Signature")
    private String operatorSignature;
    
    @Column(name = "Maintenance_Signature")
    private String maintenanceSignature;
    
    @Column(name = "Feedback", columnDefinition = "TEXT")
    private String feedback;
    
    @Column(name = "Remarks", columnDefinition = "TEXT")
    private String remarks;
    
    @OneToMany(mappedBy = "maintenanceLog", cascade = CascadeType.ALL)
    private List<MaintenancePartsUsed> partsUsed;
    
    @OneToOne(mappedBy = "maintenanceLog", cascade = CascadeType.ALL)
    private MaintenanceReading readings;
}

