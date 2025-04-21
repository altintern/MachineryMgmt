package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Long> {
    EquipmentCategory findByName(String name);
    boolean existsByName(String name);
    
}

