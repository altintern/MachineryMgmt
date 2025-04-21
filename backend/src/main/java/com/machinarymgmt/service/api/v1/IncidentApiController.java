package com.machinarymgmt.service.api.v1;
import com.machinarymgmt.service.api.IncidentsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.data.model.IncidentReport;
import com.machinarymgmt.service.dto.IncidentReportDto;
import com.machinarymgmt.service.dto.IncidentReportListResponse;
import com.machinarymgmt.service.dto.IncidentReportRequestDto;
import com.machinarymgmt.service.api.mapper.IncidentReportMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.IncidentReportService;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.dto.IncidentReportResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL )
public class IncidentApiController implements IncidentsApi {
   private final IncidentReportService incidentReportService;
   private final EquipmentService equipmentService;
   private final ProjectService projectService;

   private final IncidentReportMapper incidentReportMapper;
   private final ApiResponseBuilder responseBuilder;

//   @Override
//   public ResponseEntity<IncidentReportResponse> createIncident(@Valid IncidentReportDto incidentReportDto) throws Exception {
//      // Validate equipment exists
//      Optional<Equipment> equipmentOpt = equipmentService.findById(incidentReportDto.getEquipmentId());
//      if (equipmentOpt.isEmpty()) {
//         throw new Exception("Equipment not found with id: " + incidentReportDto.getEquipmentId());
//      }
//
//      // Validate project exists
//      Optional<Project> projectOpt = projectService.findById(incidentReportDto.getProjectId());
//      if (projectOpt.isEmpty()) {
//         throw new Exception("Project not found with id: " + incidentReportDto.getProjectId());
//      }
//
//      // Validate incident type exists
////      Optional<IncidentType> typeOpt = incidentTypeService.findById(incidentReportDto.getIncidentTypeId());
////      if (typeOpt.isEmpty()) {
////         throw new Exception("Incident type not found with id: " + incidentReportDto.getIncidentTypeId());
////      }
////
////      // Create incident report with references
////      IncidentReport report = incidentReportMapper.fromDtoWithReferences(
////            incidentReportDto,
////            equipmentOpt.get(),
////            projectOpt.get(),
////            typeOpt.get(),
////            null); // Status will be set by the service layer
//
//      // Save the report
////      IncidentReport savedReport = incidentReportService.save(report);
//
//      // Create response
////      IncidentReportDto savedDto = incidentReportMapper.toDto(savedReport);
//      IncidentReportResponse response = new IncidentReportResponse();
//      MachinaryMgmtBaseApiResponse baseResponse = (MachinaryMgmtBaseApiResponse) responseBuilder.buildSuccessApiResponse("Incident report created successfully");
//      response.setBaseResponse(baseResponse);
   ////      response.setData(savedDto);
//
//      return new ResponseEntity<>(response, HttpStatus.CREATED);
//   }




   @Override
   public ResponseEntity<IncidentReportResponse> getIncidentById(Long id) throws Exception {
      // Find the incident by ID
      Optional<IncidentReport> incidentOpt = incidentReportService.findById(id);

      if (incidentOpt.isEmpty()) {
         throw new Exception("Incident not found with id: " + id);
      }

      // Convert to DTO
      IncidentReportDto incidentDto = incidentReportMapper.toDto(incidentOpt.get());

      // Create response
      IncidentReportResponse response = (IncidentReportResponse) incidentReportMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Incident retrieved successfully"));
      response.setData(incidentDto);
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteIncident(Long id) throws Exception {

      incidentReportService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = incidentReportMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Incident Report api deleted Succesfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);

      // public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMake(Long id) throws Exception {
      //    makeService.deleteById(id);
      //    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse =
      //            makeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Make api deleted Succesfully"));
      //    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateIncident(Long id, @Valid IncidentReportRequestDto incidentReportRequestDto) throws Exception {
      IncidentReport existingIncidentReport = incidentReportService.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
      incidentReportMapper.updateIncidentReportFromDto(incidentReportRequestDto, existingIncidentReport);
      IncidentReport updatedIncidentReport = incidentReportService.save(existingIncidentReport);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = incidentReportService.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Make API updated successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
      // Make existingMake = makeService.findById(id).orElseThrow(() -> new Exception("Make not found"));
      // makeMapper.updateMakeFromDto(makeRequestDto, existingMake);
      // Make updatedMake = makeService.save(existingMake);  //Optional if you want to return the updated make
      // MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = makeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Make API updated successfully"));
      // return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

   @Override
   public ResponseEntity<IncidentReportListResponse> getAllIncidents() throws Exception {
      List<IncidentReportDto> incidentReportDto = incidentReportService.findAllDto();
      IncidentReportListResponse incidentReportListResponse = incidentReportMapper.toDtoList(responseBuilder.buildSuccessApiResponse("All Incident Reports are retrieved successfully"));
      incidentReportListResponse.setData(incidentReportDto);
      return ResponseEntity.ok(incidentReportListResponse);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> createIncident(
           @Valid IncidentReportRequestDto incidentReportRequestDto) throws Exception {
      // TODO Auto-generated method stub
      IncidentReport incidentReport = incidentReportMapper.toEntity(incidentReportRequestDto);
      incidentReportService.save(incidentReport);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = incidentReportMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Incident report created successfully"));
      return new ResponseEntity<>(machinaryMgmtBaseApiResponse,HttpStatus.CREATED);
   }


}





//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<IncidentReportDto>>> getAllIncidents(
//            @RequestParam(required = false, defaultValue = "0") Integer page,

//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<IncidentReport> incidentsPage = incidentReportService.findAll(pageable);
//        List<IncidentReportDto> incidentDtos = incidentsPage.getContent().stream()
//                .map(incidentReportMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(incidentDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<IncidentReportDto>> getIncidentById(@PathVariable Long id) {
//        return incidentReportService.findById(id)
//                .map(incident -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(incidentReportMapper.toDto(incident))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Incident not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/date-range")
//    public ResponseEntity<BaseApiResponse<List<IncidentReportDto>>> getIncidentsByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            Pageable pageable) {
//        Page<IncidentReport> incidentsPage = incidentReportService.findByIncidentDateBetween(startDate, endDate, pageable);
//        List<IncidentReportDto> incidentDtos = incidentsPage.getContent().stream()
//                .map(incidentReportMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(incidentDtos));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<IncidentReportDto>> createIncident(
//            @Valid @RequestBody IncidentReportDto incidentDto) {
//        Optional<Equipment> equipmentOpt = equipmentService.findById(incidentDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + incidentDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(incidentDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + incidentDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<IncidentType> typeOpt = incidentTypeService.findById(incidentDto.getTypeId());
//        if (typeOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Incident type not found with id: " + incidentDto.getTypeId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<StatusEntity> statusOpt = statusEntityService.findById(incidentDto.getStatusId());
//        if (statusOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Status not found with id: " + incidentDto.getStatusId(),
//                    ErrorType.NOT_FOUND));
//        }

//        IncidentReport incident = incidentReportMapper.fromDtoWithReferences(
//                incidentDto,
//                equipmentOpt.get(),
//                projectOpt.get(),
//                typeOpt.get(),
//                statusOpt.get());

//        IncidentReport savedIncident = incidentReportService.save(incident);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                incidentReportMapper.toDto(savedIncident),
//                "Incident created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<IncidentReportDto>> updateIncident(
//            @PathVariable Long id,
//            @Valid @RequestBody IncidentReportDto incidentDto) {
//        if (!incidentReportService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Incident not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(incidentDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + incidentDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(incidentDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + incidentDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<IncidentType> typeOpt = incidentTypeService.findById(incidentDto.getTypeId());
//        if (typeOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Incident type not found with id: " + incidentDto.getTypeId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<StatusEntity> statusOpt = statusEntityService.findById(incidentDto.getStatusId());
//        if (statusOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Status not found with id: " + incidentDto.getStatusId(),
//                    ErrorType.NOT_FOUND));
//        }

//        IncidentReport existingIncident = incidentReportService.findById(id).get();
//        incidentReportMapper.updateEntityFromDto(incidentDto, existingIncident);
//        existingIncident.setEquipment(equipmentOpt.get());
//        existingIncident.setProject(projectOpt.get());
//        existingIncident.setType(typeOpt.get());
//        existingIncident.setStatus(statusOpt.get());

//        IncidentReport updatedIncident = incidentReportService.save(existingIncident);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                incidentReportMapper.toDto(updatedIncident),
//                "Incident updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteIncident(@PathVariable Long id) {
//        if (!incidentReportService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Incident not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        incidentReportService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Incident deleted successfully"));
//    }


