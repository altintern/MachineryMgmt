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
@Table(name = "Make")
public class Make {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MakeID")
    private Long id;
    
    @Column(name = "Make_Name", nullable = false, unique = true)
    private String name;
    
    @OneToMany(mappedBy = "make")
    private List<Model> models;
}

