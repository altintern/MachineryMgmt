package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();
    Page<Project> findAll(Pageable pageable);
    Optional<Project> findById(Long id);
    Optional<Project> findByName(String name);
    List<Project> findByLocationContaining(String location);
    Project save(Project project);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
}

