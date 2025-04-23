package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.MaintenanceApi;
import com.machinarymgmt.service.api.MaintenancePartUsedApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.api.data.model.Project;

import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.ItemService;
import com.machinarymgmt.service.api.service.MachineryMaintenanceLogService;
import com.machinarymgmt.service.api.service.MaintenancePartsUsedService;
import com.machinarymgmt.service.dto.DesignationRequestDto;
//import com.machinarymgmt.service.api.service.MachineryMaintenancePartUsedService;
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
import com.machinarymgmt.service.dto.MaintenancePartUsedListResponse;
import com.machinarymgmt.service.dto.MaintenancePartUsedResponse;
import com.machinarymgmt.service.dto.PettyCashTransactionRequestDto;
import com.machinarymgmt.service.api.mapper.MaintenancePartUsedMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.machinarymgmt.service.dto.MaintenancePartUsedDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.machinarymgmt.service.dto.MaintenancePartUsedRequestDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class MaintenancePartUsedController implements MaintenancePartUsedApi{

      private final MaintenancePartUsedMapper maintenancePartUsedMapper;
      private final MaintenancePartsUsedService maintenancePartsUsedService;
      private final ApiResponseBuilder responseBuilder;
      private final ItemService itemService;
      private final MachineryMaintenanceLogService machineryMaintenanceLogService;
      @Override
      public ResponseEntity<MaintenancePartUsedListResponse> getAllMaintenancePartused() throws Exception {
         // TODO Auto-generated method stub
         List<MaintenancePartUsedDto> maintenancePartUsedDtosList=  maintenancePartUsedMapper.toDtoList(maintenancePartsUsedService.findAll());
         MaintenancePartUsedListResponse maintenancePartUsedListResponse=maintenancePartUsedMapper.toMaintenancePartUsedListResponse(responseBuilder.buildSuccessApiResponse("Maintenance Part used build successfully"));
         maintenancePartUsedListResponse.setData(maintenancePartUsedDtosList);
         return ResponseEntity.ok(maintenancePartUsedListResponse);
            }
      @Override
      public ResponseEntity<MaintenancePartUsedResponse> getMaintenancePartUsedById(Long id) throws Exception {
         // TODO Auto-generated method stub
         MaintenancePartUsedDto maintenancePartUsedDto= maintenancePartUsedMapper.toDto(maintenancePartsUsedService.findById(id).orElseThrow(() -> new Exception("Maintenance part used not found")));
         MaintenancePartUsedResponse maintenancePartUsedResponse= maintenancePartUsedMapper.toMaintenancePartUsedResponse(responseBuilder.buildSuccessApiResponse("Maintenance Partused build by ID successfully"));
         maintenancePartUsedResponse.setData(maintenancePartUsedDto);
         return ResponseEntity.ok(maintenancePartUsedResponse);
      }
      @Override
      public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMaintenancePartUsed(Long id) throws Exception {
         // TODO Auto-generated method stub
         maintenancePartsUsedService.deleteById(id);
         MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= maintenancePartUsedMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance Part used deleted successfully"));
         return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
      }


    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateMaintenancePartUsed(
            @PathVariable Long id,
            @Valid @RequestBody MaintenancePartUsedRequestDto dto){

        // Step 1: Fetch existing MaintenancePartUsed
        MaintenancePartsUsed existing = maintenancePartsUsedService.findById(id)
                .orElseThrow(() -> new RuntimeException("Maintenance part used not found"));

        // Step 2: Fetch associated Item
        Item item = itemService.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        MachineryMaintenanceLog machineryMaintenanceLog= machineryMaintenanceLogService.findById(dto.getMaintenanceLogId()).orElseThrow(()-> new RuntimeException("Maintenance Log not found"));

        // Step 3: Update fields
        maintenancePartUsedMapper.updateEntityFromDto(dto, existing);
        existing.setItem(item);
        existing.setMaintenanceLog(machineryMaintenanceLog);

        // Step 4: Save and respond
        maintenancePartsUsedService.save(existing);
        MachinaryMgmtBaseApiResponse response = maintenancePartUsedMapper
                .toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance part used details updated successfully"));

        return ResponseEntity.ok(response);
    }

    @Override
      public ResponseEntity<MachinaryMgmtBaseApiResponse> createMaintenancePartUsed(
            @Valid MaintenancePartUsedRequestDto maintenancePartUsedRequestDto) throws Exception {
         MachineryMaintenanceLog machineryMaintenanceLog= machineryMaintenanceLogService.findById(maintenancePartUsedRequestDto.getMaintenanceLogId())
         .orElseThrow(() -> new Exception("Maintenance Log not found with id: " + maintenancePartUsedRequestDto.getMaintenanceLogId()));
         Item item= itemService.findById(maintenancePartUsedRequestDto.getItemId())
         .orElseThrow(() -> new Exception("Item not found with id: " + maintenancePartUsedRequestDto.getItemId()));
         maintenancePartsUsedService.save(maintenancePartUsedMapper.fromDtoWithReferences(maintenancePartUsedRequestDto, machineryMaintenanceLog, item));
         MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= maintenancePartUsedMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance Part used created successfully"));
         return new ResponseEntity<>(machinaryMgmtBaseApiResponse, HttpStatus.CREATED);
      }
       
    


   // @Override
   // public ResponseEntity<MaintenanceLogResponse> createMaintenanceLog(
   //      @Valid MaintenanceLogRequestDto maintenanceLogRequestDto) throws Exception {
   //  // TODO Auto-generated method stub
   //  MachineryMaintenanceLog machineryMaintenanceLog= maintenanceLogMapper.toEntity(maintenanceLogRequestDto);
   //  MachineryMaintenanceLog machineryMaintenanceLogsaved= maintenanceLogService.save(machineryMaintenanceLog);
   //  MaintenanceLogResponse maintenanceLogResponse= maintenanceLogMapper.toMaintenanceLogResponse(responseBuilder.buildSuccessApiResponse("Maintenance Log Created successfully"));
   //  return new ResponseEntity<>(maintenanceLogResponse, HttpStatus.CREATED);
   // }
 

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

