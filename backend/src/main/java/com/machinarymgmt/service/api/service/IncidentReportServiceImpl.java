package com.machinarymgmt.service.api.service;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.IncidentReportRepository;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.IncidentReport;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.IncidentReportDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.api.mapper.IncidentReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncidentReportServiceImpl implements IncidentReportService {
    
    private final IncidentReportRepository incidentReportRepository;
    private final IncidentReportMapper incidentReportMapper;
    
    @Override
    public List<IncidentReport> findAll() {
        return incidentReportRepository.findAll();
    }
    
    @Override
    public Page<IncidentReport> findAll(Pageable pageable) {
        return incidentReportRepository.findAll(pageable);
    }
    
    @Override
    public Optional<IncidentReport> findById(Long id) {
        return incidentReportRepository.findById(id);
    }
    
    @Override
    public List<IncidentReport> findByEquipment(Equipment equipment) {
        return incidentReportRepository.findByEquipment(equipment);
    }
    
    @Override
    public List<IncidentReport> findByProject(Project project) {
        return incidentReportRepository.findByProject(project);
    }
    
    @Override
    public Page<IncidentReport> findByIncidentDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return incidentReportRepository.findByIncidentDateBetween(startDate, endDate, pageable);
    }
    
    @Override
    public IncidentReport save(IncidentReport incidentReport) {
        return incidentReportRepository.save(incidentReport);
    }
    
    @Override
    public void deleteById(Long id) {
        incidentReportRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return incidentReportRepository.existsById(id);
    }
    
    @Override
    public List<IncidentReportDto> findAllDto() {
        return incidentReportMapper.toDtoList(findAll());
    }
    
    @Override
    public Page<IncidentReportDto> findAllDto(Pageable pageable) {
        return findAll(pageable).map(incidentReportMapper::toDto);
    }
    
    @Override
    public Optional<IncidentReportDto> findDtoById(Long id) {
        return findById(id).map(incidentReportMapper::toDto);
    }

    @Override
    public MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse successApiResponse) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toBaseApiResponse'");
    }
}

