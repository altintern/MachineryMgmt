package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.EmployeeRepository;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.api.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    
    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    
    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }
    
    @Override
    public List<Employee> findByDepartment(Department department) {
        return employeeRepository.findByDepartment(department);
    }
    
    @Override
    public List<Employee> findByDesignation(Designation designation) {
        return employeeRepository.findByDesignation(designation);
    }
    
    @Override
    public Page<Employee> findByNameContaining(String name, Pageable pageable) {
        return employeeRepository.findByNameContaining(name, pageable);
    }
    
    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }
    
    @Override
    public List<EmployeeDto> findAllDto() {
        return employeeMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<EmployeeDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(employeeMapper::toDto);
    }
    
    @Override
    public Optional<EmployeeDto> findDtoById(Long id) {
        return findById(id).map(employeeMapper::toDto);
    }
}

