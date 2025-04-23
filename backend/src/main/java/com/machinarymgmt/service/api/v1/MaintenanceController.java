package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.MaintenanceApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.MachineryMaintenanceLogService;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.EquipmentUtilizationDto;
import com.machinarymgmt.service.dto.EquipmentUtilizationListResponse;
import com.machinarymgmt.service.dto.EquipmentUtilizationRequestDto;
import com.machinarymgmt.service.dto.EquipmentUtilizationResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MaintenanceLogDto;
import com.machinarymgmt.service.dto.MaintenanceLogListResponse;
import com.machinarymgmt.service.dto.MaintenanceLogRequestDto;
import com.machinarymgmt.service.dto.MaintenanceLogResponse;
import com.machinarymgmt.service.dto.PettyCashTransactionRequestDto;
import com.machinarymgmt.service.api.mapper.MaintenanceLogMapper;
import com.machinarymgmt.service.api.mapper.MaintenancePartUsedMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class MaintenanceController implements MaintenanceApi {

   private final MachineryMaintenanceLogService maintenanceLogService;
   private final MaintenanceLogMapper maintenanceLogMapper;
   private final EquipmentService equipmentService;
   private final ApiResponseBuilder responseBuilder;
   private final MaintenancePartUsedMapper maintenancePartsUsedMapper;
    
   @Override
   public ResponseEntity<MaintenanceLogListResponse> getAllMaintenanceLogs()throws Exception {
    // TODO Auto-generated method stub
    List<MaintenanceLogDto> maintenanceLogDtosList=  maintenanceLogMapper.toDtoList(maintenanceLogService.findAll());
    MaintenanceLogListResponse maintenanceLogListResponse= maintenanceLogMapper.toMaintenanceLogListResponse(responseBuilder.buildSuccessApiResponse("Maintenance Log build successfully"));
    maintenanceLogListResponse.data(maintenanceLogDtosList);
    return ResponseEntity.ok(maintenanceLogListResponse);
   }
   @Override
   public ResponseEntity<MaintenanceLogResponse> getMaintenanceLogById(Long id) throws Exception {
    // TODO Auto-generated method stub
    MaintenanceLogDto maintenanceLogDto= maintenanceLogMapper.toDto(maintenanceLogService.findById(id).orElseThrow(() -> new Exception("Maintenance Log not found")));
    MaintenanceLogResponse maintenanceLogResponse= maintenanceLogMapper.toMaintenanceLogResponse(responseBuilder.buildSuccessApiResponse("Maintenance Log build by ID successfull"));
    maintenanceLogResponse.setData(maintenanceLogDto);
    return ResponseEntity.ok(maintenanceLogResponse);
   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMaintenanceLog(Long id) throws Exception {
      // TODO Auto-generated method stub
      maintenanceLogService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= maintenanceLogMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance log deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateMaintenanceLog(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceLogRequestDto dto) {

        // Step 1: Validate and fetch the existing log
        MachineryMaintenanceLog existingLog = maintenanceLogService.findById(id)
                .orElseThrow(() -> new RuntimeException("Machinery maintenance log not found"));

        // Step 2: Validate and fetch associated Equipment
        Equipment equipment = equipmentService.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        // Step 3: Update fields
        maintenanceLogMapper.updateEntityFromDto(dto, existingLog);
        existingLog.setEquipment(equipment);

        // Step 4: Save updated entity
        maintenanceLogService.save(existingLog);

        // Step 5: Build response
        MachinaryMgmtBaseApiResponse response = maintenanceLogMapper
                .toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Machinery maintenance updated successfully"));

        return ResponseEntity.ok(response);
    }


    @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> createMaintenanceLog(
           @Valid MaintenanceLogRequestDto maintenanceLogRequestDto) throws Exception {
       // Fetch the Equipment by ID
       Equipment equipment = equipmentService.findById(maintenanceLogRequestDto.getEquipmentId())
               .orElseThrow(() -> new Exception("Equipment not found with id: " + maintenanceLogRequestDto.getEquipmentId()));
       
       // Map the DTO to the entity
       MachineryMaintenanceLog machineryMaintenanceLog = maintenanceLogMapper.toEntity(maintenanceLogRequestDto);
   
       // Set the equipment field in the entity
       machineryMaintenanceLog.setEquipment(equipment);
   
       // Save the entity
       MachineryMaintenanceLog savedMachineryMaintenanceLog = maintenanceLogService.save(machineryMaintenanceLog);
       
       // Build the response
       MachinaryMgmtBaseApiResponse apiResponse = maintenanceLogMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Machinery maintenance created successfully"));
       
       return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
   }
   

//    @GetMapping
//    public ResponseEntity<BaseApiResponse<MachineryMaintenanceLog>> getAllMaintenanceLogs(Pageable pageable) {
//        Page<MachineryMaintenanceLog> logs = maintenanceLogService.findAll(pageable);
//        return ResponseEntity.ok(responseBuilder.buildPagedResponse(logs));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MachineryMaintenanceLog>> getMaintenanceLogById(@PathVariable Long id) {
//        return maintenanceLogService.findById(id)
//                .map(log -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(log)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Maintenance log not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/equipment/{equipmentId}")
//    public ResponseEntity<BaseApiResponse<List<MachineryMaintenanceLog>>> getMaintenanceLogsByEquipment(@PathVariable Long equipmentId) {
//        Optional<Equipment> equipment = equipmentService.findById(equipmentId);
//        if (equipment.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + equipmentId,
//                    ErrorType.NOT_FOUND));
//        }
//        List<MachineryMaintenanceLog> logs = maintenanceLogService.findByEquipment(equipment.get());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(logs));
//    }

//    @GetMapping("/date-range")
//    public ResponseEntity<BaseApiResponse<List<MachineryMaintenanceLog>>> getMaintenanceLogsByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        List<MachineryMaintenanceLog> logs = maintenanceLogService.findByDateBetween(startDate, endDate);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(logs));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<MachineryMaintenanceLog>> createMaintenanceLog(@RequestBody MachineryMaintenanceLog log) {
//        MachineryMaintenanceLog savedLog = maintenanceLogService.save(log);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(savedLog, "Maintenance log created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MachineryMaintenanceLog>> updateMaintenanceLog(@PathVariable Long id, @RequestBody MachineryMaintenanceLog log) {
//        if (!maintenanceLogService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Maintenance log not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        log.setId(id);
//        MachineryMaintenanceLog updatedLog = maintenanceLogService.save(log);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(updatedLog, "Maintenance log updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteMaintenanceLog(@PathVariable Long id) {
//        if (!maintenanceLogService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Maintenance log not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        maintenanceLogService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Maintenance log deleted successfully"));
//    }
}

