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
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private Long id;
    
    @Column(name = "Employee_Name", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "DesignationID", nullable = false)
    private Designation designation;
    
    @ManyToOne
    @JoinColumn(name = "DeptID", nullable = false)
    private Department department;
    
    @Column(name = "Remarks")
    private String remarks;
    
    @OneToMany(mappedBy = "employee")
    private List<EmployeeAssignment> assignments;
    
    @OneToMany(mappedBy = "employee")
    private List<OvertimeReport> overtimeReports;
}

