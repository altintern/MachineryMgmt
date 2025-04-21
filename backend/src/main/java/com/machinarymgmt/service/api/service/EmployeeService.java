package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAll();
    Page<Employee> findAll(Pageable pageable);
    Optional<Employee> findById(Long id);
    List<Employee> findByDepartment(Department department);
    List<Employee> findByDesignation(Designation designation);
    Page<Employee> findByNameContaining(String name, Pageable pageable);
    Employee save(Employee employee);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<EmployeeDto> findAllDto();
    Page<EmployeeDto> findAllDto(Pageable pageable);
    Optional<EmployeeDto> findDtoById(Long id);
    //Employee createEmployee(EmployeeRequestDto dto) throws Exception;

}

