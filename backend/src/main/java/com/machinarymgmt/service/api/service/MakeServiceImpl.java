package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.MakeRepository;
import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.dto.MakeDto;
import com.machinarymgmt.service.api.mapper.MakeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MakeServiceImpl implements MakeService {
    
    private final MakeRepository makeRepository;
    private final MakeMapper makeMapper;
    
    @Override
    public List<Make> findAll() {
        return makeRepository.findAll();
    }

    @Override
    public Optional<Make> findById(Long id) {
        return makeRepository.findById(id);
    }

    @Override
    public Optional<Make> findByName(String name) {
        return Optional.ofNullable(makeRepository.findByName(name));
    }
    
    @Override
    public Make save(Make make) {
        return makeRepository.save(make);
    }
    
    @Override
    public void deleteById(Long id) {
        makeRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return makeRepository.existsById(id);
    }
    
    @Override
    public boolean existsByName(String name) {
        return makeRepository.existsByName(name);
    }
    
    @Override
    public List<MakeDto> findAllDto() {
        return makeMapper.toDtoList(findAll());
    }
}

