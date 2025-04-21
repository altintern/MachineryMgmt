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
@Table(name = "Dept")
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DeptID")
    private Long id;
    
    @Column(name = "Dept_Name", nullable = false, unique = true)
    private String name;

    @Column(name="Department_Description", nullable=true)
    private String description;
    
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

