package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.MastAnchorageApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeListResponse;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MastAnchorageDetailsDto;
import com.machinarymgmt.service.dto.MastAnchorageDetailsListResponse;
import com.machinarymgmt.service.dto.MastAnchorageDetailsRequestDto;
import com.machinarymgmt.service.dto.MastAnchorageDetailsResponse;
import com.machinarymgmt.service.api.mapper.MastAnchorageDetailsMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.MastAnchorageDetailsService;
import com.machinarymgmt.service.api.service.ProjectService;
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
@RequestMapping(BASE_URL )
public class MastAnchorageDetailsApiController implements MastAnchorageApi {

   private final MastAnchorageDetailsService detailsService;
   private final ProjectService projectService;
   private final EquipmentService equipmentService;
   private final MastAnchorageDetailsMapper detailsMapper;
   private final ApiResponseBuilder responseBuilder;


   @Override
   public ResponseEntity<MastAnchorageDetailsListResponse> getAllMastAnchorage() throws Exception {
    // TODO Auto-generated method stub
    List<MastAnchorageDetailsDto> mastAnchorageDetailsDtosList= detailsMapper.toDtoList(detailsService.findAll());
    MastAnchorageDetailsListResponse mastAnchorageDetailsListResponse= detailsMapper.toMastAnchorageDetailsListResponse(responseBuilder.buildSuccessApiResponse("Mast Anchorage Details build successfully"));
    mastAnchorageDetailsListResponse.data(mastAnchorageDetailsDtosList);
    return ResponseEntity.ok(mastAnchorageDetailsListResponse);
   }
   @Override
   public ResponseEntity<MastAnchorageDetailsResponse> getMastAnchorageById(Long id) throws Exception {
    // TODO Auto-generated method stub
    MastAnchorageDetailsDto mastAnchorageDetailsDto= detailsMapper.toDto(detailsService.findById(id).orElseThrow(() -> new Exception("Mast Anchorage Details not found")));
    MastAnchorageDetailsResponse mastAnchorageDetailsResponse= detailsMapper.toMastAnchorageDetailsResponse(responseBuilder.buildSuccessApiResponse("Mast Anchorage Details build by ID successfull"));
    mastAnchorageDetailsResponse.setData(mastAnchorageDetailsDto);
    return ResponseEntity.ok(mastAnchorageDetailsResponse);
   }
    @Override
    public ResponseEntity<Object> createMastAnchorage(@Valid MastAnchorageDetailsRequestDto mastAnchorageDetailsRequestDto)
            throws Exception {

        // ✅ Fetch equipment
        Equipment equipment = equipmentService.findById(mastAnchorageDetailsRequestDto.getEquipmentId())
                .orElseThrow(() -> new Exception("Equipment not found with ID: " + mastAnchorageDetailsRequestDto.getEquipmentId()));

        // ✅ Fetch project
        Project project = projectService.findById(mastAnchorageDetailsRequestDto.getProjectId())
                .orElseThrow(() -> new Exception("Project not found with ID: " + mastAnchorageDetailsRequestDto.getProjectId()));

        // ✅ Map to entity
        MastAnchorageDetails mastAnchorageDetails = detailsMapper.toEntity(mastAnchorageDetailsRequestDto);

        // ✅ Set foreign keys
        mastAnchorageDetails.setEquipment(equipment);
        mastAnchorageDetails.setProject(project);

        // ✅ Save
        MastAnchorageDetails savedDetails = detailsService.save(mastAnchorageDetails);

        // ✅ Response
        MastAnchorageDetailsResponse response = detailsMapper.toMastAnchorageDetailsResponse(
                responseBuilder.buildSuccessApiResponse("Mast Anchorage Details created successfully"));
        response.setData(detailsMapper.toDto(savedDetails));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateMastAnchorage(Long id,
                                                                            @Valid MastAnchorageDetailsRequestDto mastAnchorageDetailsRequestDto) throws Exception {
        // Fetch existing MastAnchorageDetails
        MastAnchorageDetails exisitingMastAnchorageDetails = detailsService.findById(id)
                .orElseThrow(() -> new Exception("Mast Anchorage details not found"));

        // Update entity fields with values from DTO
        detailsMapper.updateEntityFromDto(mastAnchorageDetailsRequestDto, exisitingMastAnchorageDetails);

        // Ensure foreign key relations are set if needed (project and equipment)
        Project project = projectService.findById(mastAnchorageDetailsRequestDto.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));
        Equipment equipment = equipmentService.findById(mastAnchorageDetailsRequestDto.getEquipmentId())
                .orElseThrow(() -> new Exception("Equipment not found"));

        exisitingMastAnchorageDetails.setProject(project);
        exisitingMastAnchorageDetails.setEquipment(equipment);

        // Save the updated entity
        MastAnchorageDetails updatedMastAnchorageDetails = detailsService.save(exisitingMastAnchorageDetails);

        // Create response
        MachinaryMgmtBaseApiResponse response = detailsMapper.toBaseApiResponse(
                responseBuilder.buildSuccessApiResponse("Mast Anchorage details updated successfully"));

        return ResponseEntity.ok(response);
    }

    @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMastAnchorage(Long id) throws Exception {
      // TODO Auto-generated method stub
      detailsService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= detailsMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Mast Anchorage details deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<MastAnchorageDetailsDto>>> getAllDetails(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<MastAnchorageDetails> detailsPage = detailsService.findAll(pageable);
//        List<MastAnchorageDetailsDto> detailsDtos = detailsPage.getContent().stream()
//                .map(detailsMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(detailsDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MastAnchorageDetailsDto>> getDetailsById(@PathVariable Long id) {
//        return detailsService.findById(id)
//                .map(details -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(detailsMapper.toDto(details))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Mast anchorage details not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/status/{status}")
//    public ResponseEntity<BaseApiResponse<List<MastAnchorageDetailsDto>>> getDetailsByStatus(@PathVariable String status) {
//        List<MastAnchorageDetails> details = detailsService.findByStatus(status);
//        List<MastAnchorageDetailsDto> detailsDtos = details.stream()
//                .map(detailsMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(detailsDtos));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<MastAnchorageDetailsDto>> createDetails(@Valid @RequestBody MastAnchorageDetailsDto detailsDto) {
//        Optional<Project> projectOpt = projectService.findById(detailsDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + detailsDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(detailsDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + detailsDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        MastAnchorageDetails details = detailsMapper.fromDtoWithReferences(
//                detailsDto,
//                projectOpt.get(),
//                equipmentOpt.get());

//        MastAnchorageDetails savedDetails = detailsService.save(details);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                detailsMapper.toDto(savedDetails),
//                "Mast anchorage details created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MastAnchorageDetailsDto>> updateDetails(
//            @PathVariable Long id,
//            @Valid @RequestBody MastAnchorageDetailsDto detailsDto) {
//        if (!detailsService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Mast anchorage details not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(detailsDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + detailsDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(detailsDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + detailsDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        MastAnchorageDetails existingDetails = detailsService.findById(id).get();
//        detailsMapper.updateEntityFromDto(detailsDto, existingDetails);
//        existingDetails.setProject(projectOpt.get());
//        existingDetails.setEquipment(equipmentOpt.get());

//        MastAnchorageDetails updatedDetails = detailsService.save(existingDetails);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                detailsMapper.toDto(updatedDetails),
//                "Mast anchorage details updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteDetails(@PathVariable Long id) {
//        if (!detailsService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Mast anchorage details not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        detailsService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Mast anchorage details deleted successfully"));
//    }
}

