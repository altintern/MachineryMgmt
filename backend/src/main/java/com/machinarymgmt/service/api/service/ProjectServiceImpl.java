package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.ProjectRepository;
import com.machinarymgmt.service.api.data.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    
    private final ProjectRepository projectRepository;
    
    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }
    
    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }
    
    @Override
    public Optional<Project> findByName(String name) {
        return Optional.ofNullable(projectRepository.findByName(name));
    }
    
    @Override
    public List<Project> findByLocationContaining(String location) {
        return projectRepository.findByLocationContaining(location);
    }
    
    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }
    
    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return projectRepository.existsById(id);
    }
    
    @Override
    public boolean existsByName(String name) {
        return projectRepository.existsByName(name);
    }
}

