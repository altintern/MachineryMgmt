 package com.machinarymgmt.service.api.service;

 import com.machinarymgmt.service.api.data.MastAnchorageDetailsRepository;
 import com.machinarymgmt.service.api.data.model.Equipment;
 import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
 import com.machinarymgmt.service.api.data.model.Project;
 import com.machinarymgmt.service.dto.MastAnchorageDetailsDto;
 import com.machinarymgmt.service.api.mapper.MastAnchorageDetailsMapper;
 import lombok.RequiredArgsConstructor;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.stereotype.Service;

 import java.util.List;
 import java.util.Optional;

 @Service
 @RequiredArgsConstructor
 public class MastAnchorageDetailsServiceImpl implements MastAnchorageDetailsService {
    
     private final MastAnchorageDetailsRepository detailsRepository;
     private final MastAnchorageDetailsMapper detailsMapper;
    
     @Override
     public List<MastAnchorageDetails> findAll() {
         return detailsRepository.findAll();
     }
    
     @Override
     public Page<MastAnchorageDetails> findAll(Pageable pageable) {
         return detailsRepository.findAll(pageable);
     }
    
     @Override
     public Optional<MastAnchorageDetails> findById(Long id) {
         return detailsRepository.findById(id);
     }
    
     @Override
     public List<MastAnchorageDetails> findByProject(Project project) {
         return detailsRepository.findByProject(project);
     }
    
     @Override
     public List<MastAnchorageDetails> findByEquipment(Equipment equipment) {
         return detailsRepository.findByEquipment(equipment);
     }
    
     @Override
     public List<MastAnchorageDetails> findByStatus(String status) {
         return detailsRepository.findByStatus(status);
     }
    
     @Override
     public MastAnchorageDetails save(MastAnchorageDetails details) {
         return detailsRepository.save(details);
     }
    
     @Override
     public void deleteById(Long id) {
         detailsRepository.deleteById(id);
     }
    
     @Override
     public boolean existsById(Long id) {
         return detailsRepository.existsById(id);
     }
    
     @Override
     public List<MastAnchorageDetailsDto> findAllDto() {
         return detailsMapper.toDtoList(findAll());
     }
    
     @Override
     public Page<MastAnchorageDetailsDto> findAllDto(Pageable pageable) {
         return findAll(pageable).map(detailsMapper::toDto);
     }
    
     @Override
     public Optional<MastAnchorageDetailsDto> findDtoById(Long id) {
         return findById(id).map(detailsMapper::toDto);
     }
 }

