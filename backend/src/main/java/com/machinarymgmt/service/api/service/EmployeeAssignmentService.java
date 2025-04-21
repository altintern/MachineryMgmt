package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.EmployeeAssignment;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EmployeeAssignmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeAssignmentService {
    List<EmployeeAssignment> findAll();
    Page<EmployeeAssignment> findAll(Pageable pageable);
    Optional<EmployeeAssignment> findById(Long id);
    List<EmployeeAssignment> findByEmployee(Employee employee);
    List<EmployeeAssignment> findByProject(Project project);
    List<EmployeeAssignment> findByEquipment(Equipment equipment);
    EmployeeAssignment save(EmployeeAssignment assignment);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<EmployeeAssignmentDto> findAllDto();
    Page<EmployeeAssignmentDto> findAllDto(Pageable pageable);
    Optional<EmployeeAssignmentDto> findDtoById(Long id);
}

