package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.StockStatementApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.StockStatement;
import com.machinarymgmt.service.dto.StockStatementDto;
import com.machinarymgmt.service.api.mapper.StockStatementMapper;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.ItemService;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.api.service.StockStatementService;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.ProjectDto;
import com.machinarymgmt.service.dto.ProjectListResponse;
import com.machinarymgmt.service.dto.ProjectRequestDto;
import com.machinarymgmt.service.dto.ProjectResponse;
import com.machinarymgmt.service.dto.StockStatementListResponse;
import com.machinarymgmt.service.dto.StockStatementRequestDto;
import com.machinarymgmt.service.dto.StockStatementResponse;

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
public class StockStatementApiController implements StockStatementApi{

   private final StockStatementService stockStatementService;
   private final ProjectService projectService;
   private final ItemService itemService;
   private final EquipmentService equipmentService;
 
   private final StockStatementMapper stockStatementMapper;
   private final ApiResponseBuilder responseBuilder;


   @Override
   public ResponseEntity<StockStatementListResponse> getStockStatement() throws Exception {
       // TODO Auto-generated method stub
       List<StockStatementDto> stockStatementDtos= stockStatementMapper.toDtoList(stockStatementService.findAll());
       StockStatementListResponse stockStatementListResponse= stockStatementMapper.toStockStatementListResponse(responseBuilder.buildSuccessApiResponse("Stock statement build successfully"));
       stockStatementListResponse.setData(stockStatementDtos);
       return ResponseEntity.ok(stockStatementListResponse);
   }


   @Override
   public ResponseEntity<StockStatementResponse> getStockStatementByID(Long id) throws Exception {
    // TODO Auto-generated method stub
    StockStatementDto stockStatementDto= stockStatementMapper.toDto(stockStatementService.findById(id).orElseThrow(() -> new Exception("Stock statement not found")));
    StockStatementResponse stockStatementResponse= stockStatementMapper.toStockStatementResponse(responseBuilder.buildSuccessApiResponse("Stock statement build byy ID successfull"));
    stockStatementResponse.data(stockStatementDto);
    return ResponseEntity.ok(stockStatementResponse);
   }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> createStockStatement(@Valid StockStatementRequestDto dto) {
        Project project = projectService.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Item item = itemService.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        Equipment equipment = equipmentService.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        stockStatementService.save(stockStatementMapper.fromDtoWithReferences(dto, project, item, equipment));
        MachinaryMgmtBaseApiResponse response = stockStatementMapper
                .toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Stock Statement created successfully"));

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateStockStatement(
            @PathVariable Long id,
            @Valid @RequestBody StockStatementRequestDto dto){

        // Check if the stock statement exists
        StockStatement existing = stockStatementService.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock statement not found"));

        // Validate and fetch related entities
        Project project = projectService.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Item item = itemService.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));
        Equipment equipment = equipmentService.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        // Update entity from DTO + references
        stockStatementMapper.updateEntityFromDto(dto, existing);
        existing.setProject(project);
        existing.setItem(item);
        existing.setEquipment(equipment);

        stockStatementService.save(existing);

        MachinaryMgmtBaseApiResponse response = stockStatementMapper
                .toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Stock Statement updated successfully"));

        return ResponseEntity.ok(response);
    }




    @Override
public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteStockStatement(Long id) throws Exception {
    // TODO Auto-generated method stub
    stockStatementService.deleteById(id);
    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=  stockStatementMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Stock Statement deleted successfully"));
    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
}

//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<StockStatementDto>>> getAllStockStatements(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<StockStatement> statementsPage = stockStatementService.findAll(pageable);
//        List<StockStatementDto> statementDtos = statementsPage.getContent().stream()
//                .map(stockStatementMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(statementDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<StockStatementDto>> getStockStatementById(@PathVariable Long id) {
//        return stockStatementService.findById(id)
//                .map(statement -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(stockStatementMapper.toDto(statement))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Stock statement not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/month-year")
//    public ResponseEntity<BaseApiResponse<List<StockStatementDto>>> getStockStatementsByMonthAndYear(
//            @RequestParam Integer month,
//            @RequestParam Integer year) {
//        List<StockStatement> statements = stockStatementService.findByMonthAndYear(month, year);
//        List<StockStatementDto> statementDtos = statements.stream()
//                .map(stockStatementMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(statementDtos));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<StockStatementDto>> createStockStatement(@Valid @RequestBody StockStatementDto statementDto) {
//        Optional<Project> projectOpt = projectService.findById(statementDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + statementDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Item> itemOpt = itemService.findById(statementDto.getItemId());
//        if (itemOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Item not found with id: " + statementDto.getItemId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(statementDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + statementDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        StockStatement statement = stockStatementMapper.fromDtoWithReferences(
//                statementDto,
//                projectOpt.get(),
//                itemOpt.get(),
//                equipmentOpt.get());

//        StockStatement savedStatement = stockStatementService.save(statement);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                stockStatementMapper.toDto(savedStatement),
//                "Stock statement created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<StockStatementDto>> updateStockStatement(
//            @PathVariable Long id,
//            @Valid @RequestBody StockStatementDto statementDto) {
//        if (!stockStatementService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Stock statement not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(statementDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + statementDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Item> itemOpt = itemService.findById(statementDto.getItemId());
//        if (itemOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Item not found with id: " + statementDto.getItemId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(statementDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + statementDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        StockStatement existingStatement = stockStatementService.findById(id).get();
//        stockStatementMapper.updateEntityFromDto(statementDto, existingStatement);
//        existingStatement.setProject(projectOpt.get());
//        existingStatement.setItem(itemOpt.get());
//        existingStatement.setEquipment(equipmentOpt.get());

//        StockStatement updatedStatement = stockStatementService.save(existingStatement);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                stockStatementMapper.toDto(updatedStatement),
//                "Stock statement updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteStockStatement(@PathVariable Long id) {
//        if (!stockStatementService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Stock statement not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        stockStatementService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Stock statement deleted successfully"));
//    }
}

