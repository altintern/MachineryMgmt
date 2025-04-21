package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.DepartmentRepository;
import com.machinarymgmt.service.api.data.model.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    
    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
    
    @Override
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }
    
    @Override
    public Optional<Department> findByName(String name) {
        return Optional.ofNullable(departmentRepository.findByName(name));
    }
    
    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }
    
    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return departmentRepository.existsById(id);
    }
    
    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}

