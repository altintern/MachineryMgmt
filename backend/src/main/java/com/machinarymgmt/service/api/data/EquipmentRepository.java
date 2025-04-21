package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByProject(Project project);
    List<Equipment> findByCategory(EquipmentCategory category);
    List<Equipment> findByModel(Model model);
    Page<Equipment> findByNameContaining(String name, Pageable pageable);
    Equipment findByAssetCode(String assetCode);
    boolean existsByAssetCode(String assetCode);
    Optional<Equipment> findByName(String name);
    boolean existsByName(String name);

}

