package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByName(String name);
    List<Project> findByLocationContaining(String location);
    boolean existsByName(String name);
}

