// package com.machinarymgmt.service.api.v1;

// import com.machinarymgmt.service.api.EquipmentsApi;
// import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
// import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
// import com.machinarymgmt.service.api.config.dto.ErrorType;
// import com.machinarymgmt.service.api.data.model.Equipment;
// import com.machinarymgmt.service.api.data.model.EquipmentCategory;
// import com.machinarymgmt.service.api.data.model.Model;
// import com.machinarymgmt.service.api.data.model.Project;
// import com.machinarymgmt.service.dto.EquipmentDto;
// import com.machinarymgmt.service.dto.EquipmentRequestDto;
// import com.machinarymgmt.service.api.mapper.EquipmentMapper;
// import com.machinarymgmt.service.api.service.EquipmentCategoryService;
// import com.machinarymgmt.service.api.service.EquipmentService;
// import com.machinarymgmt.service.api.service.ModelService;
// import com.machinarymgmt.service.api.service.ProjectService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import jakarta.validation.Valid;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import static com.machinarymgmt.service.api.utils.Constants.EQUIPMENTAPI_URL;
// import static com.machinarymgmt.service.api.utils.Constants.EQUIPMENT_URL;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping(EQUIPMENTAPI_URL)
// public class EquipmentApiController implements EquipmentsApi {

//     private final EquipmentService equipmentService;
//     private final ProjectService projectService;
//     private final EquipmentCategoryService categoryService;
//     private final ModelService modelService;
//     private final EquipmentMapper equipmentMapper;
//     private final ApiResponseBuilder responseBuilder;
// //
// //    @GetMapping
// //    public ResponseEntity<BaseApiResponse<List<EquipmentDto>>> getAllEquipment(
// //            @RequestParam(required = false, defaultValue = "0") Integer page,
// //            @RequestParam(required = false, defaultValue = "10") Integer size,
// //            Pageable pageable) {
// //        Page<Equipment> equipmentPage = equipmentService.findAll(pageable);
// //        List<EquipmentDto> equipmentDtos = equipmentPage.getContent().stream()
// //                .map(equipmentMapper::toDto)
// //                .collect(Collectors.toList());
// //        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(equipmentDtos));
// //    }

// //    @GetMapping("/{id}")
// //    public ResponseEntity<BaseApiResponse<EquipmentDto>> getEquipmentById(@PathVariable Long id) {
// //        return equipmentService.findById(id)
// //                .map(equipment -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(equipmentMapper.toDto(equipment))))
// //                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                        "Equipment not found with id: " + id,
// //                        ErrorType.NOT_FOUND)));
// //    }

// //    @PostMapping
// //    public ResponseEntity<BaseApiResponse<EquipmentDto>> createEquipment(@Valid @RequestBody EquipmentRequestDto requestDto) {
// //        if (equipmentService.existsByAssetCode(requestDto.getAssetCode())) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Equipment already exists with asset code: " + requestDto.getAssetCode(),
// //                    ErrorType.DUPLICATE));
// //        }

// //        Optional<Project> projectOpt = projectService.findById(requestDto.getProjectId());
// //        if (projectOpt.isEmpty()) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Project not found with id: " + requestDto.getProjectId(),
// //                    ErrorType.NOT_FOUND));
// //        }

// //        Optional<EquipmentCategory> categoryOpt = Optional.empty();
// //        if (requestDto.getCategoryId() != null) {
// //            categoryOpt = categoryService.findById(requestDto.getCategoryId());
// //            if (categoryOpt.isEmpty()) {
// //                return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                        "Category not found with id: " + requestDto.getCategoryId(),
// //                        ErrorType.NOT_FOUND));
// //            }
// //        }

// //        Optional<Model> modelOpt = Optional.empty();
// //        if (requestDto.getModelId() != null) {
// //            modelOpt = modelService.findById(requestDto.getModelId());
// //            if (modelOpt.isEmpty()) {
// //                return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                        "Model not found with id: " + requestDto.getModelId(),
// //                        ErrorType.NOT_FOUND));
// //            }
// //        }

// //        Equipment equipment = equipmentMapper.toEntity(requestDto);
// //        equipment.setProject(projectOpt.get());
// //        categoryOpt.ifPresent(equipment::setCategory);
// //        modelOpt.ifPresent(equipment::setModel);

// //        Equipment savedEquipment = equipmentService.save(equipment);
// //        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
// //                equipmentMapper.toDto(savedEquipment),
// //                "Equipment created successfully"));
// //    }

// //    @PutMapping("/{id}")
// //    public ResponseEntity<BaseApiResponse<EquipmentDto>> updateEquipment(
// //            @PathVariable Long id,
// //            @Valid @RequestBody EquipmentRequestDto requestDto) {
// //        if (!equipmentService.existsById(id)) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Equipment not found with id: " + id,
// //                    ErrorType.NOT_FOUND));
// //        }

// //        Optional<Equipment> existingEquipmentOpt = equipmentService.findById(id);
// //        if (existingEquipmentOpt.isEmpty()) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Equipment not found with id: " + id,
// //                    ErrorType.NOT_FOUND));
// //        }

// //        Equipment existingEquipment = existingEquipmentOpt.get();
// //        if (!existingEquipment.getAssetCode().equals(requestDto.getAssetCode()) &&
// //                equipmentService.existsByAssetCode(requestDto.getAssetCode())) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Equipment already exists with asset code: " + requestDto.getAssetCode(),
// //                    ErrorType.DUPLICATE));
// //        }

// //        Optional<Project> projectOpt = projectService.findById(requestDto.getProjectId());
// //        if (projectOpt.isEmpty()) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Project not found with id: " + requestDto.getProjectId(),
// //                    ErrorType.NOT_FOUND));
// //        }

// //        Optional<EquipmentCategory> categoryOpt = Optional.empty();
// //        if (requestDto.getCategoryId() != null) {
// //            categoryOpt = categoryService.findById(requestDto.getCategoryId());
// //            if (categoryOpt.isEmpty()) {
// //                return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                        "Category not found with id: " + requestDto.getCategoryId(),
// //                        ErrorType.NOT_FOUND));
// //            }
// //        }

// //        Optional<Model> modelOpt = Optional.empty();
// //        if (requestDto.getModelId() != null) {
// //            modelOpt = modelService.findById(requestDto.getModelId());
// //            if (modelOpt.isEmpty()) {
// //                return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                        "Model not found with id: " + requestDto.getModelId(),
// //                        ErrorType.NOT_FOUND));
// //            }
// //        }

// //        equipmentMapper.updateEntityFromDto(requestDto, existingEquipment);
// //        existingEquipment.setProject(projectOpt.get());
// //        existingEquipment.setCategory(categoryOpt.orElse(null));
// //        existingEquipment.setModel(modelOpt.orElse(null));

// //        Equipment updatedEquipment = equipmentService.save(existingEquipment);
// //        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
// //                equipmentMapper.toDto(updatedEquipment),
// //                "Equipment updated successfully"));
// //    }

// //    @DeleteMapping("/{id}")
// //    public ResponseEntity<BaseApiResponse<Void>> deleteEquipment(@PathVariable Long id) {
// //        if (!equipmentService.existsById(id)) {
// //            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
// //                    "Equipment not found with id: " + id,
// //                    ErrorType.NOT_FOUND));
// //        }
// //        equipmentService.deleteById(id);
// //        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Equipment deleted successfully"));
// //    }
// }

