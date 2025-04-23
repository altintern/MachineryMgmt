package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.PettyCashApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MaterialsConsumptionTransaction;
import com.machinarymgmt.service.api.data.model.PettyCashTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.mapper.PettyCashTransactionMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.ItemService;
import com.machinarymgmt.service.api.service.PettyCashTransactionService;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.dto.PettyCashTransactionListResponse;
import com.machinarymgmt.service.dto.PettyCashTransactionResponse;
import com.machinarymgmt.service.dto.ProjectRequestDto;
import com.machinarymgmt.service.dto.DepartmentRequestDto;
import com.machinarymgmt.service.dto.DepartmentResponse;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MaterialsConsumptionTransactionRequest;
import com.machinarymgmt.service.dto.ModelDto;
import com.machinarymgmt.service.dto.ModelResponse;
import com.machinarymgmt.service.dto.PettyCashTransactionDto;
import com.machinarymgmt.service.dto.PettyCashTransactionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL )
public class PettyCashTransactionApiController implements PettyCashApi {

    private final PettyCashTransactionService transactionService;
    private final ProjectService projectService;
    private final EquipmentService equipmentService;
    private final ItemService itemService;
    private final PettyCashTransactionMapper transactionMapper;
    private final ApiResponseBuilder responseBuilder;
    @Override
    public ResponseEntity<PettyCashTransactionListResponse> getPettyCash() throws Exception {

        List<PettyCashTransactionDto> transactionsDto = transactionMapper.toDtoList( transactionService.findAll());
        PettyCashTransactionListResponse pettyCashTransactionListResponse = transactionMapper.toDtoList(responseBuilder.buildSuccessApiResponse("All petty cash transactions are retrieved successfully"));
        pettyCashTransactionListResponse.data(transactionsDto);
        System.out.println(transactionsDto);
        System.out.println(pettyCashTransactionListResponse);
        return ResponseEntity.ok(pettyCashTransactionListResponse);
        // TODO Auto-generated method stub
    }
    @Override
    public ResponseEntity<PettyCashTransactionResponse> getPettyCashByID(Long id) throws Exception {
        PettyCashTransactionDto pettyCashTransactionDto = transactionMapper.toDto(transactionService.findById(id).orElseThrow(() -> new Exception("Petty cash transaction not found")));
        PettyCashTransactionResponse pettyCashTransactionResponse = transactionMapper.toPettyCashTransactionResponse(responseBuilder.buildSuccessApiResponse("Petty cash transaction retrieved successfully"));
        pettyCashTransactionResponse.data(pettyCashTransactionDto);
        System.out.println(pettyCashTransactionResponse);
        return ResponseEntity.ok(pettyCashTransactionResponse);
//    ModelDto modelDto = modelMapper.toDto(modelService.findById(id).orElseThrow(() -> new Exception("Project not found")));
//       ModelResponse modelResponse = modelMapper.toModelResponse(responseBuilder.buildSuccessApiResponse("Model retrieved successfully"));
//       modelResponse.data(modelDto);
//       System.out.println(modelResponse);

    }


    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> createPettyCash(
            @Valid PettyCashTransactionRequestDto pettyCashTransactionRequestDto) throws Exception {
        // TODO Auto-generated method stub
        Project project = projectService.findById(pettyCashTransactionRequestDto.getProjectId())
                .orElseThrow(() -> new Exception("Project not found with id: " + pettyCashTransactionRequestDto.getProjectId()));
        Equipment equipment = equipmentService.findById(pettyCashTransactionRequestDto.getEquipmentId())
                .orElseThrow(() -> new Exception("Equipment not found with id: " + pettyCashTransactionRequestDto.getEquipmentId()));
        Item item = itemService.findById(pettyCashTransactionRequestDto.getItemId())
                .orElseThrow(() -> new Exception("Item not found with id: " + pettyCashTransactionRequestDto.getItemId()));
        transactionService.save(transactionMapper.fromDtoWithReferences(pettyCashTransactionRequestDto, equipment, item, project));
        MachinaryMgmtBaseApiResponse mApiResponse = transactionMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Petty Cash Created successfully"));
        return new ResponseEntity<>(mApiResponse, HttpStatus.CREATED);
    }



    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updatePettyCash(Long id,
                                                                        @Valid PettyCashTransactionRequestDto pettyCashTransactionRequestDto) throws Exception {
        // TODO Auto-generated method stub
        PettyCashTransaction existingPettyCashTransaction= transactionService.findById(id).orElseThrow(() -> new Exception("PettyCash Transaction not found"));
        transactionMapper.updateEntityFromDto(pettyCashTransactionRequestDto, existingPettyCashTransaction);

        Project project = projectService.findById(pettyCashTransactionRequestDto.getProjectId())
                .orElseThrow(() -> new Exception("Project not found"));
        Equipment equipment = equipmentService.findById(pettyCashTransactionRequestDto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Item item = itemService.findById(pettyCashTransactionRequestDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Set those entities in the transaction
        existingPettyCashTransaction.setProject(project);
        existingPettyCashTransaction.setItem(item);
        existingPettyCashTransaction.setEquipment(equipment);
        PettyCashTransaction updatedPettyCashTransaction= transactionService.save(existingPettyCashTransaction);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= transactionMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Petty cash transaction details updated successfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> deletePettyCash(Long id) throws Exception {
        // Check if transaction exists
        if (!transactionService.existsById(id)) {
            throw new Exception("Petty cash transaction not found with id: " + id);
        }

        // Delete the transaction
        transactionService.deleteById(id);

        // Create and return success response
        BaseApiResponse baseResponse = responseBuilder.buildSuccessApiResponse("Petty cash transaction deleted successfully");
        MachinaryMgmtBaseApiResponse response = new MachinaryMgmtBaseApiResponse();
        response.setRespType(baseResponse.getRespType());
        response.setMetadata(baseResponse.getMetadata());
        response.setStatus(baseResponse.getStatus());
        response.setMessages(baseResponse.getMessages());
        return ResponseEntity.ok(response);
    }








//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<PettyCashTransactionDto>>> getAllTransactions(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<PettyCashTransaction> transactionsPage = transactionService.findAll(pageable);
//        List<PettyCashTransactionDto> transactionDtos = transactionsPage.getContent().stream()
//                .map(transactionMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(transactionDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<PettyCashTransactionDto>> getTransactionById(@PathVariable Long id) {
//        return transactionService.findById(id)
//                .map(transaction -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(transactionMapper.toDto(transaction))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Petty cash transaction not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/date-range")
//    public ResponseEntity<BaseApiResponse<List<PettyCashTransactionDto>>> getTransactionsByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
//            Pageable pageable) {
//        Page<PettyCashTransaction> transactionsPage = transactionService.findByReportDateBetween(startDate, endDate, pageable);
//        List<PettyCashTransactionDto> transactionDtos = transactionsPage.getContent().stream()
//                .map(transactionMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(transactionDtos));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<PettyCashTransactionDto>> createTransaction(@Valid @RequestBody PettyCashTransactionDto transactionDto) {
//        Optional<Project> projectOpt = projectService.findById(transactionDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + transactionDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(transactionDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + transactionDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Item> itemOpt = itemService.findById(transactionDto.getItemId());
//        if (itemOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Item not found with id: " + transactionDto.getItemId(),
//                    ErrorType.NOT_FOUND));
//        }

//        PettyCashTransaction transaction = transactionMapper.fromDtoWithReferences(
//                transactionDto,
//                projectOpt.get(),
//                equipmentOpt.get(),
//                itemOpt.get());

//        PettyCashTransaction savedTransaction = transactionService.save(transaction);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                transactionMapper.toDto(savedTransaction),
//                "Petty cash transaction created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<PettyCashTransactionDto>> updateTransaction(
//            @PathVariable Long id,
//            @Valid @RequestBody PettyCashTransactionDto transactionDto) {
//        if (!transactionService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Petty cash transaction not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(transactionDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + transactionDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(transactionDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + transactionDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Item> itemOpt = itemService.findById(transactionDto.getItemId());
//        if (itemOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Item not found with id: " + transactionDto.getItemId(),
//                    ErrorType.NOT_FOUND));
//        }

//        PettyCashTransaction existingTransaction = transactionService.findById(id).get();
//        transactionMapper.updateEntityFromDto(transactionDto, existingTransaction);
//        existingTransaction.setProject(projectOpt.get());
//        existingTransaction.setEquipment(equipmentOpt.get());
//        existingTransaction.setItem(itemOpt.get());

//        PettyCashTransaction updatedTransaction = transactionService.save(existingTransaction);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                transactionMapper.toDto(updatedTransaction),
//                "Petty cash transaction updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteTransaction(@PathVariable Long id) {
//        if (!transactionService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Petty cash transaction not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        transactionService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Petty cash transaction deleted successfully"));
//    }
}

