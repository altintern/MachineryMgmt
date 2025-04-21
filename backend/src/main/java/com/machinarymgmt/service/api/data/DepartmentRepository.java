package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByName(String name);
    boolean existsByName(String name);
}

