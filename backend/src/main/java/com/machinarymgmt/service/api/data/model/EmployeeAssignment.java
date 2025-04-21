package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Employee_Assignment")
public class EmployeeAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AssignmentID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "EquipmentID", nullable = false)
    private Equipment equipment;

    @Column(name = "Joining_Date")
    private LocalDate joiningDate;
}

