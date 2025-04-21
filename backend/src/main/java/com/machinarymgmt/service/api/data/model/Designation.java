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
@Table(name = "Designation")
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DesignationID")
    private Long id;
    
    @Column(name = "Designation_Name", nullable = false, unique = true)
    private String name;

    @Column(name="Designation_Description", nullable=true)
    private String description;
    
    @OneToMany(mappedBy = "designation")
    private List<Employee> employees;
}

