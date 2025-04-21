package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.dto.DesignationDto;

import java.util.List;
import java.util.Optional;

public interface DesignationService {
    List<Designation> findAll();
    Optional<Designation> findById(Long id);
    Optional<Designation> findByName(String name);
    Designation save(Designation designation);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
    List<DesignationDto> findAllDto();
    Optional<DesignationDto> findDtoById(Long id);
}

