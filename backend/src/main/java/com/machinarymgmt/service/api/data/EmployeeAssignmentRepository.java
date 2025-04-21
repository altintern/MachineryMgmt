package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.EmployeeAssignment;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeAssignmentRepository extends JpaRepository<EmployeeAssignment, Long> {
    List<EmployeeAssignment> findByEmployee(Employee employee);
    List<EmployeeAssignment> findByProject(Project project);
    List<EmployeeAssignment> findByEquipment(Equipment equipment);
    boolean existsByEmployeeAndProjectAndEquipment(Employee employee, Project project, Equipment equipment);
}


