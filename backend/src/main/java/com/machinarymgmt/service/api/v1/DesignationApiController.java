package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.DesignationsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.dto.DepartmentDto;
import com.machinarymgmt.service.dto.DepartmentListResponse;
import com.machinarymgmt.service.dto.DepartmentRequestDto;
import com.machinarymgmt.service.dto.DepartmentResponse;
import com.machinarymgmt.service.dto.DesignationDto;
import com.machinarymgmt.service.api.mapper.DesignationMapper;
import com.machinarymgmt.service.api.service.DesignationService;
import com.machinarymgmt.service.dto.DesignationListResponse;
import com.machinarymgmt.service.dto.DesignationRequestDto;
import com.machinarymgmt.service.dto.DesignationResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL )
public class DesignationApiController implements DesignationsApi{

   private final DesignationService designationService;
   private final DesignationMapper designationMapper;
   private final ApiResponseBuilder responseBuilder;
   @Override
   public ResponseEntity<DesignationListResponse> getAllDesignations() throws Exception {
    // TODO Auto-generated method stub
    List<DesignationDto> designationDtosList =designationMapper.toDtoList(designationService.findAll());
    DesignationListResponse designationListResponse=designationMapper.toDesignationListResponse(responseBuilder.buildSuccessApiResponse("Designation build successfully"));
    designationListResponse.data(designationDtosList);
    return ResponseEntity.ok(designationListResponse);
   }
   @Override
   public ResponseEntity<DesignationResponse> getDesignationById(Long id) throws Exception {
    // TODO Auto-generated method stub
    DesignationDto designationDto=designationMapper.toDto(designationService.findById(id).orElseThrow(() -> new Exception("Designation not found")));
    DesignationResponse designationResponse=designationMapper.toDesignationResponse(responseBuilder.buildSuccessApiResponse("Designation build by ID successfull"));
    designationResponse.data(designationDto);
    return ResponseEntity.ok(designationResponse);
   }
   @Override
   public ResponseEntity<Object> createDesignation(@Valid DesignationRequestDto designationRequestDto) throws Exception {
    // TODO Auto-generated method stub
    Designation designation =designationMapper.toEntity(designationRequestDto);
    Designation designationsaved= designationService.save(designation);
    DesignationResponse designationResponse= designationMapper.toDesignationResponse(responseBuilder.buildSuccessApiResponse("Designation created successfully"));
    return new ResponseEntity<>(designationResponse, HttpStatus.CREATED);
   }

    @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateDesignation(Long id, @Valid DesignationRequestDto designationRequestDto)
         throws Exception {
      // TODO Auto-generated method stub
      Designation exisitingDesignation =designationService.findById(id).orElseThrow(() -> new Exception("Designation not found"));
      designationMapper.updateEntityFromDto(designationRequestDto, exisitingDesignation);
      Designation updatedDesignation = designationService.save(exisitingDesignation);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= designationMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Designation details updated successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }


@Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteDesignation(Long id) throws Exception {
      // TODO Auto-generated method stub
      designationService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= designationMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Designation deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }



//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<DesignationDto>>> getAllDesignations() {
//        List<DesignationDto> designations = designationService.findAllDto();
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(designations));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<DesignationDto>> getDesignationById(@PathVariable Long id) {
//        return designationService.findDtoById(id)
//                .map(designation -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(designation)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Designation not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<DesignationDto>> createDesignation(@Valid @RequestBody DesignationDto designationDto) {
//        if (designationService.existsByName(designationDto.getDesignationName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Designation already exists with name: " + designationDto.getDesignationName(),
//                    ErrorType.DUPLICATE));
//        }

//        Designation designation = designationMapper.toEntity(designationDto);
//        Designation savedDesignation = designationService.save(designation);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                designationMapper.toDto(savedDesignation),
//                "Designation created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<DesignationDto>> updateDesignation(
//            @PathVariable Long id,
//            @Valid @RequestBody DesignationDto designationDto) {
//        if (!designationService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Designation not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Designation existingDesignation = designationService.findById(id).get();
//        if (!existingDesignation.getName().equals(designationDto.getDesignationName()) &&
//                designationService.existsByName(designationDto.getDesignationName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Designation already exists with name: " + designationDto.getDesignationName(),
//                    ErrorType.DUPLICATE));
//        }

//        designationMapper.updateEntityFromDto(designationDto, existingDesignation);
//        Designation updatedDesignation = designationService.save(existingDesignation);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                designationMapper.toDto(updatedDesignation),
//                "Designation updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteDesignation(@PathVariable Long id) {
//        if (!designationService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Designation not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        designationService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Designation deleted successfully"));
//    }
}

