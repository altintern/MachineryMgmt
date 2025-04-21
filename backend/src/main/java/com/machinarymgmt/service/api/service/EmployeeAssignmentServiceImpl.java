package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.EmployeeAssignmentRepository;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.EmployeeAssignment;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EmployeeAssignmentDto;
import com.machinarymgmt.service.api.mapper.EmployeeAssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeAssignmentServiceImpl implements EmployeeAssignmentService {
    
    private final EmployeeAssignmentRepository assignmentRepository;
    private final EmployeeAssignmentMapper assignmentMapper;
    
    @Override
    public List<EmployeeAssignment> findAll() {
        return assignmentRepository.findAll();
    }
    
    @Override
    public Page<EmployeeAssignment> findAll(Pageable pageable) {
        return assignmentRepository.findAll(pageable);
    }
    
    @Override
    public Optional<EmployeeAssignment> findById(Long id) {
        return assignmentRepository.findById(id);
    }
    
    @Override
    public List<EmployeeAssignment> findByEmployee(Employee employee) {
        return assignmentRepository.findByEmployee(employee);
    }
    
    @Override
    public List<EmployeeAssignment> findByProject(Project project) {
        return assignmentRepository.findByProject(project);
    }
    
    @Override
    public List<EmployeeAssignment> findByEquipment(Equipment equipment) {
        return assignmentRepository.findByEquipment(equipment);
    }
    
    @Override
    public EmployeeAssignment save(EmployeeAssignment assignment) {
        return assignmentRepository.save(assignment);
    }
    
    @Override
    public void deleteById(Long id) {
        assignmentRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return assignmentRepository.existsById(id);
    }
    
    @Override
    public List<EmployeeAssignmentDto> findAllDto() {
        return assignmentMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<EmployeeAssignmentDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(assignmentMapper::toDto);
    }
    
    @Override
    public Optional<EmployeeAssignmentDto> findDtoById(Long id) {
        return findById(id).map(assignmentMapper::toDto);
    }
}

