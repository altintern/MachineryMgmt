package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.EquipmentsApi;
import com.machinarymgmt.service.api.EquipmentsUtilizationApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.EquipmentUtilizationRepository;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.EquipmentDto;
import com.machinarymgmt.service.dto.EquipmentListResponse;
import com.machinarymgmt.service.dto.EquipmentRequestDto;
import com.machinarymgmt.service.dto.EquipmentResponse;
import com.machinarymgmt.service.dto.EquipmentUtilizationDto;
import com.machinarymgmt.service.api.mapper.EquipmentUtilizationMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.EquipmentUtilizationService;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.dto.EquipmentUtilizationListResponse;
import com.machinarymgmt.service.dto.EquipmentUtilizationRequestDto;
import com.machinarymgmt.service.dto.EquipmentUtilizationResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class EquipmentUtilizationApiController implements EquipmentsUtilizationApi{

   private final EquipmentUtilizationService utilizationService;
   private final EquipmentService equipmentService;
  
   private final ProjectService projectService;
   private final EquipmentUtilizationMapper utilizationMapper;
   private final ApiResponseBuilder responseBuilder;
   @Override
   public ResponseEntity<EquipmentUtilizationListResponse> getAllEquipmentUtilization() throws Exception {
    // TODO Auto-generated method stub
    List<EquipmentUtilizationDto> equipmentUtilizationDtosList= utilizationMapper.toDtoList(utilizationService.findAll());
    EquipmentUtilizationListResponse equipmentUtilizationListResponse= utilizationMapper.toEquipmentUtilizationListResponse(responseBuilder.buildSuccessApiResponse("Equipment Utilization build successfully"));
    equipmentUtilizationListResponse.data(equipmentUtilizationDtosList);
    return ResponseEntity.ok(equipmentUtilizationListResponse);
   }
   @Override
   public ResponseEntity<EquipmentUtilizationResponse> getEquipmentUtilizationById(Long id) throws Exception {
    // TODO Auto-generated method stub
    EquipmentUtilizationDto equipmentUtilizationDto= utilizationMapper.toDto(utilizationService.findById(id).orElseThrow(() -> new Exception("Equipment Utilization not found")));
    EquipmentUtilizationResponse equipmentUtilizationResponse= utilizationMapper.toEquipmentUtilizationResponse(responseBuilder.buildSuccessApiResponse("Equipment Utilization build by Id successfull"));
    equipmentUtilizationResponse.setData(equipmentUtilizationDto);
    return ResponseEntity.ok(equipmentUtilizationResponse);
   }
//   @Override
//   public ResponseEntity<Object> createEquipmentUtilization(
//        @Valid EquipmentUtilizationRequestDto equipmentUtilizationRequestDto) throws Exception {
//    // TODO Auto-generated method stub
//    EquipmentUtilization equipmentUtilization= utilizationMapper.toEntity(equipmentUtilizationRequestDto);
//    EquipmentUtilization equipmentUtilizationsaved= utilizationService.save(equipmentUtilization);
//    EquipmentUtilizationResponse equipmentUtilizationResponse= utilizationMapper.toEquipmentUtilizationResponse(responseBuilder.buildSuccessApiResponse("Equipment utilization created successfully"));
//    return new ResponseEntity<>(equipmentUtilizationResponse, HttpStatus.CREATED);
//   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateEquipmentUtilization(Long id,
        @Valid EquipmentUtilizationRequestDto equipmentUtilizationRequestDto) throws Exception {
    // TODO Auto-generated method stub
    EquipmentUtilization existingEquipmentUtilization= utilizationService.findById(id).orElseThrow(() -> new Exception("Equipment Utilization not found"));
    utilizationMapper.updateEntityFromDto(equipmentUtilizationRequestDto, existingEquipmentUtilization);
    EquipmentUtilization updatedEquipmentUtilization=utilizationService.save(existingEquipmentUtilization);
    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= utilizationMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Equipment Utilization details Updated successfully"));
    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> createEquipmentUtilization(EquipmentUtilizationRequestDto equipmentUtilizationRequestDto) throws Exception {
        EquipmentUtilization equipmentUtilization= utilizationMapper.toEntity(equipmentUtilizationRequestDto);
        EquipmentUtilization equipmentUtilizationsaved= utilizationService.save(equipmentUtilization);
        MachinaryMgmtBaseApiResponse mgmtBaseApiResponse= utilizationMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Equipment utilization created successfully"));
    return new ResponseEntity<>(mgmtBaseApiResponse, HttpStatus.CREATED);
    }

    @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteEquipmentUtilization(Long id) throws Exception {
    // TODO Auto-generated method stub
    utilizationService.deleteById(id);
    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= utilizationMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Equipment Utilization details deleted successfully"));
    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

 
//    @Override
//    public ResponseEntity<EquipmentResponse> createEquipment(@Valid EquipmentRequestDto equipmentRequestDto)
//         throws Exception {
//     // TODO Auto-generated method stub
//     Equipment equipment= equipmentMapper.toEntity(equipmentRequestDto);
//     Equipment equipmentsaved= equipmentService.save(equipment);
//     EquipmentResponse equipmentResponse= equipmentMapper.toEquipmentResponse(responseBuilder.buildSuccessApiResponse("Equipment Created successfully"));
//     return new ResponseEntity<>(equipmentResponse, HttpStatus.CREATED);
//    }
//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<EquipmentUtilizationDto>>> getAllUtilizations(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<EquipmentUtilization> utilizationsPage = utilizationService.findAll(pageable);
//        List<EquipmentUtilizationDto> utilizationDtos = utilizationsPage.getContent().stream()
//                .map(utilizationMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(utilizationDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EquipmentUtilizationDto>> getUtilizationById(@PathVariable Long id) {
//        return utilizationService.findById(id)
//                .map(utilization -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(utilizationMapper.toDto(utilization))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Equipment utilization not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/month-year")
//    public ResponseEntity<BaseApiResponse<List<EquipmentUtilizationDto>>> getUtilizationsByMonthAndYear(
//            @RequestParam Integer month,
//            @RequestParam Integer year) {
//        List<EquipmentUtilization> utilizations = utilizationService.findByMonthAndYear(month, year);
//        List<EquipmentUtilizationDto> utilizationDtos = utilizations.stream()
//                .map(utilizationMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(utilizationDtos));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<EquipmentUtilizationDto>> createUtilization(@Valid @RequestBody EquipmentUtilizationDto utilizationDto) {
//        Optional<Equipment> equipmentOpt = equipmentService.findById(utilizationDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + utilizationDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(utilizationDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + utilizationDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        EquipmentUtilization utilization = utilizationMapper.fromDtoWithReferences(
//                utilizationDto,
//                equipmentOpt.get(),
//                projectOpt.get());

//        EquipmentUtilization savedUtilization = utilizationService.save(utilization);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                utilizationMapper.toDto(savedUtilization),
//                "Equipment utilization created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EquipmentUtilizationDto>> updateUtilization(
//            @PathVariable Long id,
//            @Valid @RequestBody EquipmentUtilizationDto utilizationDto) {
//        if (!utilizationService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment utilization not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(utilizationDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + utilizationDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(utilizationDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + utilizationDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        EquipmentUtilization existingUtilization = utilizationService.findById(id).get();
//        utilizationMapper.updateEntityFromDto(utilizationDto, existingUtilization);
//        existingUtilization.setEquipment(equipmentOpt.get());
//        existingUtilization.setProject(projectOpt.get());

//        EquipmentUtilization updatedUtilization = utilizationService.save(existingUtilization);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                utilizationMapper.toDto(updatedUtilization),
//                "Equipment utilization updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteUtilization(@PathVariable Long id) {
//        if (!utilizationService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment utilization not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        utilizationService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Equipment utilization deleted successfully"));
//    }
}

