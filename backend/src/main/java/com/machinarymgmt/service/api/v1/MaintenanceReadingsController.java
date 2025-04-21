package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.MaintenanceApi;
import com.machinarymgmt.service.api.MaintenanceReadingApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.MachineryMaintenanceLogService;
import com.machinarymgmt.service.api.service.MaintenanceReadingService;
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
import com.machinarymgmt.service.dto.MaintenanceReadingRequestDto;
import com.machinarymgmt.service.dto.MaintenanceReadingResponse;
import com.machinarymgmt.service.dto.MaintenanceLogResponse;
import com.machinarymgmt.service.dto.MaintenanceReadingDto;
import com.machinarymgmt.service.dto.MaintenanceReadingListResponse;
import com.machinarymgmt.service.dto.MaintenanceReadingResponse;
import com.machinarymgmt.service.api.mapper.MaintenanceLogMapper;
import com.machinarymgmt.service.api.mapper.MaintenanceReadingMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class MaintenanceReadingsController implements MaintenanceReadingApi{


   private final MaintenanceReadingService maintenanceReadingService;
   private final MaintenanceReadingMapper maintenanceReadingMapper;
   private final EquipmentService equipmentService;
   private final ApiResponseBuilder responseBuilder;
   @Override
   public ResponseEntity<MaintenanceReadingListResponse> getAllMaintenanceReading() throws Exception {
      // TODO Auto-generated method stub
      List<MaintenanceReadingDto> maintenanceReadingDtosList= maintenanceReadingMapper.toDtoList(maintenanceReadingService.findAll());
      MaintenanceReadingListResponse maintenanceReadingListResponse= maintenanceReadingMapper.toMaintenancereadingListResponse(responseBuilder.buildSuccessApiResponse("Maintenance Reading build successfully"));
      maintenanceReadingListResponse.setData(maintenanceReadingDtosList);
      return ResponseEntity.ok(maintenanceReadingListResponse);
   }
   @Override
   public ResponseEntity<MaintenanceReadingResponse> getMaintenanceReadingById(Long id) throws Exception {
      // TODO Auto-generated method stub
      MaintenanceReadingDto maintenanceReadingDto =maintenanceReadingMapper.toDto(maintenanceReadingService.findById(id).orElseThrow(() -> new Exception("Maintenance Readings not found")));
      MaintenanceReadingResponse maintenanceReadingResponse= maintenanceReadingMapper.toMaintenanceReadingResponse(responseBuilder.buildSuccessApiResponse("Maintenance Reading build by ID successfully"));
      maintenanceReadingResponse.setData(maintenanceReadingDto);
      return ResponseEntity.ok(maintenanceReadingResponse);
   }
   
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMaintenanceReading(Long id) throws Exception {
      // TODO Auto-generated method stub
      maintenanceReadingService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= maintenanceReadingMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance Readings deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateMaintenanceReading(Long id,
         @Valid MaintenanceReadingRequestDto maintenanceReadingRequestDto) throws Exception {
      // TODO Auto-generated method stub
      MaintenanceReading existinMaintenanceReading = maintenanceReadingService.findById(id).orElseThrow(() -> new Exception("Machinery Maintenance Reading not found"));
      maintenanceReadingMapper.updateEntityFromDto(maintenanceReadingRequestDto, existinMaintenanceReading);
      MaintenanceReading updatMaintenanceReading= maintenanceReadingService.save(existinMaintenanceReading);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= maintenanceReadingMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance readings updated successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }
   @Override
   public ResponseEntity<MaintenanceReadingResponse> createMaintenanceReadings(
         @Valid MaintenanceReadingRequestDto maintenanceReadingRequestDto) throws Exception {
      // TODO Auto-generated method stub
      MaintenanceReading maintenanceReading = maintenanceReadingMapper.toEntity(maintenanceReadingRequestDto);
      MaintenanceReading maintenanceReadingsaved= maintenanceReadingService.save(maintenanceReading);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= maintenanceReadingMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Maintenance Reading created successfully"));
      MaintenanceReadingResponse maintenanceReadingResponse= maintenanceReadingMapper.toMaintenanceReadingResponse(responseBuilder.buildSuccessApiResponse("Maintenance Reading created successfully"));
      return new ResponseEntity<>(maintenanceReadingResponse, HttpStatus.CREATED);
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

