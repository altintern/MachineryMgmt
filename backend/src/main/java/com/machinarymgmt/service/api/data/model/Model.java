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
@Table(name = "Model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ModelID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "MakeID", nullable = false)
    private Make make;
    
    @Column(name = "Model_Name", nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "model")
    private List<Equipment> equipment;
}

