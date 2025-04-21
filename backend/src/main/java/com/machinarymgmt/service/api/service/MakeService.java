package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.dto.MakeDto;

import java.util.List;
import java.util.Optional;

public interface MakeService {
    List<Make> findAll();
    Optional<Make> findById(Long id);
    Optional<Make> findByName(String name);
    Make save(Make make);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
    List<MakeDto> findAllDto();
}

