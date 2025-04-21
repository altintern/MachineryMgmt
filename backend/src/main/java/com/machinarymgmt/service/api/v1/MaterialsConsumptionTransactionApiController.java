package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.MaterialsConsumptionApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.*;
//import com.machinarymgmt.service.api.dto.MaterialsConsumptionTransactionDto;
//import com.machinarymgmt.service.api.mapper.MaterialsConsumptionTransactionMapper;
import com.machinarymgmt.service.api.mapper.MaterialsConsumptionTransactionMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.ItemService;
//import com.machinarymgmt.service.api.service.MaterialsConsumptionTransactionService;
import com.machinarymgmt.service.api.service.MaterialsConsumptionTransactionService;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class MaterialsConsumptionTransactionApiController implements MaterialsConsumptionApi {

    private final MaterialsConsumptionTransactionService transactionService;
    private final ProjectService projectService;
    private final EquipmentService equipmentService;
    private final ItemService itemService;
    private final MaterialsConsumptionTransactionMapper transactionMapper;
    private final ApiResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> createMaterialsConsumptionTransaction(MaterialsConsumptionTransactionRequest materialsConsumptionTransactionRequest) throws Exception {
        Project project = projectService.findById(materialsConsumptionTransactionRequest.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));
        Equipment equipment = equipmentService.findById(materialsConsumptionTransactionRequest.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Item item = itemService.findById(materialsConsumptionTransactionRequest.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        MaterialsConsumptionTransaction materialsConsumptionTransaction = transactionMapper.fromDtoWithReferences(materialsConsumptionTransactionRequest,project,equipment,item);
        materialsConsumptionTransaction.setCreatedAt(LocalDateTime.now());
        transactionService.save(materialsConsumptionTransaction);
        return ResponseEntity.ok(transactionMapper.toDtoResponse(responseBuilder.buildSuccessApiResponse("material consumption transation created successfully")));
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMaterialsConsumptionTransaction(Long id) throws Exception {
        transactionService.deleteById(id);
        return ResponseEntity.ok(transactionMapper.toDtoResponse(responseBuilder.buildSuccessApiResponse("deleted successfully")));
    }

    @Override
    public ResponseEntity<MaterialsConsumptionTransactionListResponse> getAllMaterialsConsumptionTransactions() throws Exception {
        List<MaterialsConsumptionTransactionDto> transactionDtos = transactionMapper.toDtoList(transactionService.findAll());
        MaterialsConsumptionTransactionListResponse materialsConsumptionTransactionListResponse = transactionMapper.toListResponse(responseBuilder.buildSuccessApiResponse("listed all transactions succefully"));
        return ResponseEntity.ok(materialsConsumptionTransactionListResponse);
    }

    @Override
    public ResponseEntity<MaterialsConsumptionTransactionResponse> getMaterialsConsumptionTransactionById(Long id) throws Exception {
        MaterialsConsumptionTransactionDto materialsConsumptionTransactionDto = transactionMapper.toDto(transactionService.findById(id).orElseThrow(() -> new RuntimeException("Does not exists")));
        MaterialsConsumptionTransactionResponse consumptionTransactionResponse = transactionMapper.toResponse(responseBuilder.buildSuccessApiResponse("fetched the material consumption data succesfully"));
        consumptionTransactionResponse.setData(materialsConsumptionTransactionDto);
        System.out.println(materialsConsumptionTransactionDto);
        return ResponseEntity.ok(consumptionTransactionResponse);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateMaterialsConsumptionTransaction(Long id, MaterialsConsumptionTransactionRequest materialsConsumptionTransactionRequest) throws Exception {
        MaterialsConsumptionTransaction existingTransaction = transactionService.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        transactionMapper.updateEntityFromDto(materialsConsumptionTransactionRequest, existingTransaction);

        Project project = projectService.findById(materialsConsumptionTransactionRequest.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));
        Equipment equipment = equipmentService.findById(materialsConsumptionTransactionRequest.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Item item = itemService.findById(materialsConsumptionTransactionRequest.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Set those entities in the transaction
        existingTransaction.setProject(project);
        existingTransaction.setItem(item);
        existingTransaction.setEquipment(equipment);

        MaterialsConsumptionTransaction updatedTransaction = transactionService.save(existingTransaction);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = transactionMapper.toResponse(responseBuilder.buildSuccessApiResponse("updated succesfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }

//    @Override
//    public ResponseEntity<MaterialsConsumptionTransactionResponse> updateMaterialsConsumptionTransaction(Long id, MaterialsConsumptionTransactionRequest materialsConsumptionTransactionRequest) throws Exception {
//        MaterialsConsumptionTransaction existingTransaction = transactionService.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
//        transactionMapper.updateEntityFromDto(materialsConsumptionTransactionRequest, existingTransaction);
//        MaterialsConsumptionTransaction updatedTransaction = transactionService.save(existingTransaction);
//        MaterialsConsumptionTransactionResponse materialsConsumptionTransactionResponse = transactionMapper.toResponse(responseBuilder.buildSuccessApiResponse("updated succesfully"));
//        return ResponseEntity.ok(materialsConsumptionTransactionResponse);
//    }

//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<MaterialsConsumptionTransactionDto>>> getAllTransactions(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<MaterialsConsumptionTransaction> transactionsPage = transactionService.findAll(pageable);
//        List<MaterialsConsumptionTransactionDto> transactionDtos = transactionsPage.getContent().stream()
//                .map(transactionMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(transactionDtos));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MaterialsConsumptionTransactionDto>> getTransactionById(@PathVariable Long id) {
//        return transactionService.findById(id)
//                .map(transaction -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(transactionMapper.toDto(transaction))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Materials consumption transaction not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }
//
//    @GetMapping("/date-range")
//    public ResponseEntity<BaseApiResponse<List<MaterialsConsumptionTransactionDto>>> getTransactionsByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            Pageable pageable) {
//        Page<MaterialsConsumptionTransaction> transactionsPage = transactionService.findByIssueDateBetween(startDate, endDate, pageable);
//        List<MaterialsConsumptionTransactionDto> transactionDtos = transactionsPage.getContent().stream()
//                .map(transactionMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(transactionDtos));
//    }
//
//    @PostMapping
//    public ResponseEntity<BaseApiResponse<MaterialsConsumptionTransactionDto>> createTransaction(@Valid @RequestBody MaterialsConsumptionTransactionDto transactionDto) {
//        Optional<Project> projectOpt = projectService.findById(transactionDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + transactionDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }
//
//        Optional<Equipment> equipmentOpt = equipmentService.findById(transactionDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + transactionDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }
//
//        Optional<Item> itemOpt = itemService.findById(transactionDto.getItemId());
//        if (itemOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Item not found with id: " + transactionDto.getItemId(),
//                    ErrorType.NOT_FOUND));
//        }
//
//        MaterialsConsumptionTransaction transaction = transactionMapper.fromDtoWithReferences(
//                transactionDto,
//                projectOpt.get(),
//                equipmentOpt.get(),
//                itemOpt.get());
//
//        MaterialsConsumptionTransaction savedTransaction = transactionService.save(transaction);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                transactionMapper.toDto(savedTransaction),
//                "Materials consumption transaction created successfully"));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MaterialsConsumptionTransactionDto>> updateTransaction(
//            @PathVariable Long id,
//            @Valid @RequestBody MaterialsConsumptionTransactionDto transactionDto) {
//        if (!transactionService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Materials consumption transaction not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//
//        Optional<Project> projectOpt = projectService.findById(transactionDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + transactionDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }
//
//        Optional<Equipment> equipmentOpt = equipmentService.findById(transactionDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + transactionDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }
//
//        Optional<Item> itemOpt = itemService.findById(transactionDto.getItemId());
//        if (itemOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Item not found with id: " + transactionDto.getItemId(),
//                    ErrorType.NOT_FOUND));
//        }
//
//        MaterialsConsumptionTransaction existingTransaction = transactionService.findById(id).get();
//        transactionMapper.updateEntityFromDto(transactionDto, existingTransaction);
//        existingTransaction.setProject(projectOpt.get());
//        existingTransaction.setEquipment(equipmentOpt.get());
//        existingTransaction.setItem(itemOpt.get());
//
//        MaterialsConsumptionTransaction updatedTransaction = transactionService.save(existingTransaction);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                transactionMapper.toDto(updatedTransaction),
//                "Materials consumption transaction updated successfully"));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteTransaction(@PathVariable Long id) {
//        if (!transactionService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Materials consumption transaction not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        transactionService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Materials consumption transaction deleted successfully"));
//    }
}

