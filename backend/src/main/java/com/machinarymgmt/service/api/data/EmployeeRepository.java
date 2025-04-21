package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(Department department);
    List<Employee> findByDesignation(Designation designation);
    Page<Employee> findByNameContaining(String name, Pageable pageable);
    boolean existsByName(String name);
    Employee findByName(String name);
}

