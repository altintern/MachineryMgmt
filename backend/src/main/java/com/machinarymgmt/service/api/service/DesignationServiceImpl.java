package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.DesignationRepository;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.dto.DesignationDto;
import com.machinarymgmt.service.api.mapper.DesignationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {
    
    private final DesignationRepository designationRepository;
    private final DesignationMapper designationMapper;
    
    @Override
    public List<Designation> findAll() {
        return designationRepository.findAll();
    }
    
    @Override
    public Optional<Designation> findById(Long id) {
        return designationRepository.findById(id);
    }
    
    @Override
    public Optional<Designation> findByName(String name) {
        return Optional.ofNullable(designationRepository.findByName(name));
    }
    
    @Override
    public Designation save(Designation designation) {
        return designationRepository.save(designation);
    }
    
    @Override
    public void deleteById(Long id) {
        designationRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return designationRepository.existsById(id);
    }
    
    @Override
    public boolean existsByName(String name) {
        return designationRepository.existsByName(name);
    }
    
    @Override
    public List<DesignationDto> findAllDto() {
        return designationMapper.toDtoList(findAll());
    }
    
    @Override
    public Optional<DesignationDto> findDtoById(Long id) {
        return findById(id).map(designationMapper::toDto);
    }
}

