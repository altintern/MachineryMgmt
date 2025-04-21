package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.EquipmentsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.service.EquipmentService;
//import com.machinarymgmt.service.api.service.categoryService;
import com.machinarymgmt.service.dto.DepartmentRequestDto;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeListResponse;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.EquipmentDto;
import com.machinarymgmt.service.dto.EquipmentListResponse;
import com.machinarymgmt.service.dto.EquipmentRequestDto;
import com.machinarymgmt.service.dto.EquipmentResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.api.mapper.EquipmentMapper;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.api.service.EquipmentCategoryService;
import com.machinarymgmt.service.api.service.ModelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.machinarymgmt.service.api.utils.Constants.EQUIPMENT_URL;
import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

import java.util.List;
import java.util.Locale.Category;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class EquipmentController implements EquipmentsApi{

   
   private final EquipmentService equipmentService;
   private final EquipmentMapper equipmentMapper;
   private final ApiResponseBuilder responseBuilder;
   private final ModelService modelService;
   

   @Autowired
private EquipmentCategoryService equipmentCategoryService;

@Autowired
private ProjectService projectService;

   @Override
   public ResponseEntity<EquipmentListResponse> getAllEquipment()throws Exception {
    // TODO Auto-generated method stub
    List<EquipmentDto> equipmentDtosList = equipmentMapper.toDtoList(equipmentService.findAll());
    EquipmentListResponse equipmentListResponse= equipmentMapper.toEquipmentListResponse(responseBuilder.buildSuccessApiResponse("Equipment build successfully"));
    equipmentListResponse.data(equipmentDtosList);
    return ResponseEntity.ok(equipmentListResponse);
   }
   @Override
   public ResponseEntity<EquipmentResponse> getEquipmentById(Long id) throws Exception {
    // TODO Auto-generated method stub
    EquipmentDto equipmentDto=equipmentMapper.toDto(equipmentService.findById(id).orElseThrow(() -> new Exception("Equipment not found")));
    EquipmentResponse equipmentResponse=equipmentMapper.toEquipmentResponse(responseBuilder.buildSuccessApiResponse("Equipment build by ID successfull"));
    equipmentResponse.setData(equipmentDto);
    return ResponseEntity.ok(equipmentResponse);
   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateEquipment(Long id, @Valid EquipmentRequestDto equipmentRequestDto)
         throws Exception {
      // TODO Auto-generated method stub
      Equipment existingEquipment= equipmentService.findById(id).orElseThrow(() -> new Exception("Equipment not found"));
      equipmentMapper.updateEntityFromDto(equipmentRequestDto, existingEquipment);
      Equipment updatedEquipment=equipmentService.save(existingEquipment);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= equipmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Equipment details updated successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteEquipment(Long id) throws Exception {
      // TODO Auto-generated method stub
      equipmentService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= equipmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Equipment details deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> createEquipment(@Valid EquipmentRequestDto equipmentRequestDto)
         throws Exception {
      // TODO Auto-generated method stub
      EquipmentCategory category= equipmentCategoryService.findById(equipmentRequestDto.getCategoryId()).orElseThrow(() -> new Exception("Category not found"));
      Model model = modelService.findById(equipmentRequestDto.getModelId()).orElseThrow(() -> new Exception("Model not found"));
      Project project= projectService.findById(equipmentRequestDto.getProjectId()).orElseThrow(() -> new Exception("Project not found"));
      equipmentService.save(equipmentMapper.fromDtoWithReferences(equipmentRequestDto, category, model, project));
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= equipmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Equipment created successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }


// 
//    @Override
//    public ResponseEntity<MachinaryMgmtBaseApiResponse> createEquipment(@Valid EquipmentRequestDto equipmentRequestDto)
//         throws Exception {
//     // TODO Auto-generated method stub
//     Equipment equipment= equipmentMapper.toEntity(equipmentRequestDto);
//     Equipment equipmentsaved= equipmentService.save(equipment);
//     EquipmentResponse equipmentResponse= equipmentMapper.toEquipmentResponse(responseBuilder.buildSuccessApiResponse("Equipment Created successfully"));
//     return new ResponseEntity<>(equipmentResponse, HttpStatus.CREATED);
//    }


//    @GetMapping
//    public ResponseEntity<BaseApiResponse<Equipment>> getAllEquipment(Pageable pageable) {
//        Page<Equipment> equipment = equipmentService.findAll(pageable);
//        return ResponseEntity.ok(responseBuilder.buildPagedResponse(equipment));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Equipment>> getEquipmentById(@PathVariable Long id) {
//        return equipmentService.findById(id)
//                .map(equipment -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(equipment)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Equipment not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/asset-code/{assetCode}")
//    public ResponseEntity<BaseApiResponse<Equipment>> getEquipmentByAssetCode(@PathVariable String assetCode) {
//        return equipmentService.findByAssetCode(assetCode)
//                .map(equipment -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(equipment)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Equipment not found with asset code: " + assetCode,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<Equipment>> createEquipment(@RequestBody Equipment equipment) {
//        if (equipmentService.existsByAssetCode(equipment.getAssetCode())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment already exists with asset code: " + equipment.getAssetCode(),
//                    ErrorType.DUPLICATE));
//        }
//        Equipment savedEquipment = equipmentService.save(equipment);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(savedEquipment, "Equipment created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Equipment>> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
//        if (!equipmentService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        equipment.setId(id);
//        Equipment updatedEquipment = equipmentService.save(equipment);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(updatedEquipment, "Equipment updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteEquipment(@PathVariable Long id) {
//        if (!equipmentService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        equipmentService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Equipment deleted successfully"));
//    }
}

