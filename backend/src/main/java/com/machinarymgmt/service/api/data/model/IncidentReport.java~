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
@Table(name = "Incident_Report")
public class IncidentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IncidentID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "EquipmentID", nullable = false)
    private Equipment equipment;
    
    @ManyToOne
    @JoinColumn(name = "ProjectID", nullable = false)
    private Project project;
    
    @ManyToOne
    @JoinColumn(name = "TypeID", nullable = false)
    private IncidentType type;
    
    @Column(name = "Incident_Details", nullable = false, columnDefinition = "TEXT")
    private String details;
    
    @Column(name = "Incident_Date", nullable = false)
    private LocalDate incidentDate;
    
    @Column(name = "Action_Taken", nullable = false, columnDefinition = "TEXT")
    private String actionTaken;
    
    @Column(name = "Estimated_Completion_date")
    private LocalDate estimatedCompletionDate;
    
    @Column(name = "Closed_date")
    private LocalDate closedDate;
    
    @ManyToOne
    @JoinColumn(name = "StatusID", nullable = false)
    private StatusEntity status;
}

