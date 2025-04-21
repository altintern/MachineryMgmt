package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectID")
    private Long id;
    
    @Column(name = "Project_Name", nullable = false, unique = true)
    private String name;
    
    @Column(name = "Project_Location")
    private String location;

    @Column(name ="Project_Description")
    private String description;

    @Column(name="Project_StartDate")
    private String startDate;

    @Column(name="Project_EndDate")
    private String endDate;

    @Column(name= "Project_Status")
    private String status;

    @OneToMany(mappedBy = "project")
    private List<Equipment> equipment;
    
    @OneToMany(mappedBy = "project")
    private List<StockStatement> stockStatements;
    
    @OneToMany(mappedBy = "project")
    private List<EquipmentUtilization> utilizations;
    
    @OneToMany(mappedBy = "project")
    private List<IncidentReport> incidents;
    
    @OneToMany(mappedBy = "project")
    private List<EmployeeAssignment> employeeAssignments;
    
    @OneToMany(mappedBy = "project")
    private List<PettyCashTransaction> pettyCashTransactions;
    
    @OneToMany(mappedBy = "project")
    private List<MaterialsConsumptionTransaction> materialsConsumptions;
    
    @OneToMany(mappedBy = "project")
    private List<MastAnchorageDetails> mastAnchorageDetails;
}

