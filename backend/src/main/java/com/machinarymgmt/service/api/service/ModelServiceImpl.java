package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.ModelRepository;
import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.dto.ModelDto;
import com.machinarymgmt.service.api.mapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {
    
    private final ModelRepository modelRepository;
    private final MakeService makeService;
    private final ModelMapper modelMapper;
    
    @Override
    public List<Model> findAll() {
        return modelRepository.findAll();
    }
    
    @Override
    public Optional<Model> findById(Long id) {
        return modelRepository.findById(id);
    }
    
    @Override
    public List<Model> findByMake(Make make) {
        return modelRepository.findByMake(make);
    }
    
    @Override
    public Optional<Model> findByNameAndMake(String name, Make make) {
        return Optional.ofNullable(modelRepository.findByNameAndMake(name, make));
    }
    
    @Override
    public Model save(Model model) {
        return modelRepository.save(model);
    }
    
    @Override
    public void deleteById(Long id) {
        modelRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return modelRepository.existsById(id);
    }
    
    @Override
    public boolean existsByNameAndMake(String name, Make make) {
        return modelRepository.existsByNameAndMake(name, make);
    }
    
    @Override
    public List<ModelDto> findAllDto() {
        return modelMapper.toDtoList(findAll());
    }

    // @Override
    // public List<ModelDto> findDtoByMakeId(Long makeId) {
    //     return makeService.findById(makeId)
    //             .map(make -> modelMapper.toDtoList(findByMake(make)))
    //             .orElse(Collections.emptyList());
    // }

    @Override
    public Optional<ModelDto> findDtoById(Long id) {
        return findById(id).map(modelMapper::toDto);
    }

}

