package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> findAll();
    Optional<Department> findById(Long id);
    Optional<Department> findByName(String name);
    Department save(Department department);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
}

