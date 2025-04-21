package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.dto.EquipmentUtilizationDto;
import com.machinarymgmt.service.api.mapper.EquipmentUtilizationMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.EquipmentUtilizationService;
import com.machinarymgmt.service.api.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL + "/equipment-utilizations")
public class EquipmentUtilizationApiController {

    private final EquipmentUtilizationService utilizationService;
    private final EquipmentService equipmentService;
    private final ProjectService projectService;
    private final EquipmentUtilizationMapper utilizationMapper;
    private final ApiResponseBuilder responseBuilder;

    @GetMapping
    public ResponseEntity<BaseApiResponse<List<EquipmentUtilizationDto>>> getAllUtilizations(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Pageable pageable) {
        Page<EquipmentUtilization> utilizationsPage = utilizationService.findAll(pageable);
        List<EquipmentUtilizationDto> utilizationDtos = utilizationsPage.getContent().stream()
                .map(utilizationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(utilizationDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseApiResponse<EquipmentUtilizationDto>> getUtilizationById(@PathVariable Long id) {
        return utilizationService.findById(id)
                .map(utilization -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(utilizationMapper.toDto(utilization))))
                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
                        "Equipment utilization not found with id: " + id,
                        ErrorType.NOT_FOUND)));
    }

    @GetMapping("/month-year")
    public ResponseEntity<BaseApiResponse<List<EquipmentUtilizationDto>>> getUtilizationsByMonthAndYear(
            @RequestParam Integer month,
            @RequestParam Integer year) {
        List<EquipmentUtilization> utilizations = utilizationService.findByMonthAndYear(month, year);
        List<EquipmentUtilizationDto> utilizationDtos = utilizations.stream()
                .map(utilizationMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(utilizationDtos));
    }

    @PostMapping
    public ResponseEntity<BaseApiResponse<EquipmentUtilizationDto>> createUtilization(@Valid @RequestBody EquipmentUtilizationDto utilizationDto) {
        Optional<Equipment> equipmentOpt = equipmentService.findById(utilizationDto.getEquipmentId());
        if (equipmentOpt.isEmpty()) {
            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
                    "Equipment not found with id: " + utilizationDto.getEquipmentId(),
                    ErrorType.NOT_FOUND));
        }

        Optional<Project> projectOpt = projectService.findById(utilizationDto.getProjectId());
        if (projectOpt.isEmpty()) {
            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
                    "Project not found with id: " + utilizationDto.getProjectId(),
                    ErrorType.NOT_FOUND));
        }

        EquipmentUtilization utilization = utilizationMapper.fromDtoWithReferences(
                utilizationDto,
                equipmentOpt.get(),
                projectOpt.get());

        EquipmentUtilization savedUtilization = utilizationService.save(utilization);
        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
                utilizationMapper.toDto(savedUtilization),
                "Equipment utilization created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseApiResponse<EquipmentUtilizationDto>> updateUtilization(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentUtilizationDto utilizationDto) {
        if (!utilizationService.existsById(id)) {
            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
                    "Equipment utilization not found with id: " + id,
                    ErrorType.NOT_FOUND));
        }

        Optional<Equipment> equipmentOpt = equipmentService.findById(utilizationDto.getEquipmentId());
        if (equipmentOpt.isEmpty()) {
            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
                    "Equipment not found with id: " + utilizationDto.getEquipmentId(),
                    ErrorType.NOT_FOUND));
        }

        Optional<Project> projectOpt = projectService.findById(utilizationDto.getProjectId());
        if (projectOpt.isEmpty()) {
            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
                    "Project not found with id: " + utilizationDto.getProjectId(),
                    ErrorType.NOT_FOUND));
        }

        EquipmentUtilization existingUtilization = utilizationService.findById(id).get();
        utilizationMapper.updateEntityFromDto(utilizationDto, existingUtilization);
        existingUtilization.setEquipment(equipmentOpt.get());
        existingUtilization.setProject(projectOpt.get());

        EquipmentUtilization updatedUtilization = utilizationService.save(existingUtilization);
        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
                utilizationMapper.toDto(updatedUtilization),
                "Equipment utilization updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseApiResponse<Void>> deleteUtilization(@PathVariable Long id) {
        if (!utilizationService.existsById(id)) {
            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
                    "Equipment utilization not found with id: " + id,
                    ErrorType.NOT_FOUND));
        }
        utilizationService.deleteById(id);
        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Equipment utilization deleted successfully"));
    }
}

