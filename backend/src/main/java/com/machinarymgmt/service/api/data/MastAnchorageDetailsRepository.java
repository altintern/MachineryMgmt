package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MastAnchorageDetailsRepository extends JpaRepository<MastAnchorageDetails, Long> {
    List<MastAnchorageDetails> findByProject(Project project);
    List<MastAnchorageDetails> findByEquipment(Equipment equipment);
    List<MastAnchorageDetails> findByStatus(String status);
    boolean existsByProjectAndEquipment(Project project, Equipment equipment);
}

