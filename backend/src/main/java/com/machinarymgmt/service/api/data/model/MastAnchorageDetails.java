package com.machinarymgmt.service.api.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Mast_Anchorage_Details")
public class MastAnchorageDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SNo")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "EquipmentID", nullable = false)
    private Equipment equipment;
    
    @Column(name = "Status")
    private String status;
    
    @Column(name = "Location")
    private String location;
    
    @Column(name = "Mast_Available_At_Site")
    private Integer mastAvailableAtSite;
    
    @Column(name = "Mast_Fixed_At_Site")
    private Integer mastFixedAtSite;
    
    @Column(name = "Mast_idle_At_Site")
    private Integer mastIdleAtSite;
    
    @Column(name = "Total_Mast_Requirement")
    private Integer totalMastRequirement;
    
    @Column(name = "Anchorage_At_Site")
    private Integer anchorageAtSite;
    
    @Column(name = "Anchorage_Fixed_At_Site")
    private Integer anchorageFixedAtSite;
    
    @Column(name = "Anchorage_idle_At_Site")
    private Integer anchorageIdleAtSite;
    
    @Column(name = "Total_Anchorage_Requirement")
    private Integer totalAnchorageRequirement;
    
    @Column(name = "Present_Height_Of_Hoist")
    private String presentHeightOfHoist;
    
    @Column(name = "Present_Building_Height")
    private String presentBuildingHeight;
    
    @Column(name = "Total_Building_Height")
    private String totalBuildingHeight;
    
    @Column(name = "Remarks")
    private String remarks;
}

