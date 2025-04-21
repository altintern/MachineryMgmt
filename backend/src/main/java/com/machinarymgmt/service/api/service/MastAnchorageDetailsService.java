package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.MastAnchorageDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MastAnchorageDetailsService {
    List<MastAnchorageDetails> findAll();
    Page<MastAnchorageDetails> findAll(Pageable pageable);
    Optional<MastAnchorageDetails> findById(Long id);
    List<MastAnchorageDetails> findByProject(Project project);
    List<MastAnchorageDetails> findByEquipment(Equipment equipment);
    List<MastAnchorageDetails> findByStatus(String status);
    MastAnchorageDetails save(MastAnchorageDetails details);
    void deleteById(Long id);
    boolean existsById(Long id);
    List<MastAnchorageDetailsDto> findAllDto();
    Page<MastAnchorageDetailsDto> findAllDto(Pageable pageable);
    Optional<MastAnchorageDetailsDto> findDtoById(Long id);
}

