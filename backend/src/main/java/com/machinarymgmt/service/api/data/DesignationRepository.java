package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    Designation findByName(String name);
    boolean existsByName(String name);
}

