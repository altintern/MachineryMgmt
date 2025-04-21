package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.EmployeesApi;
import com.machinarymgmt.service.api.EmployeesAssignmentApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.EmployeeAssignment;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EmployeeAssignmentDto;
import com.machinarymgmt.service.api.mapper.EmployeeAssignmentMapper;
import com.machinarymgmt.service.api.mapper.EmployeeMapper;
import com.machinarymgmt.service.api.service.EmployeeAssignmentService;
import com.machinarymgmt.service.api.service.EmployeeService;
import com.machinarymgmt.service.api.service.EquipmentService;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.dto.EmployeeAssignmentListResponse;
import com.machinarymgmt.service.dto.EmployeeAssignmentRequestDto;
import com.machinarymgmt.service.dto.EmployeeAssignmentResponse;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeListResponse;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
//import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;

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
public class EmployeeAssignmentApiController implements EmployeesAssignmentApi{

   private final EmployeeAssignmentService assignmentService;
   private final EmployeeService employeeService;
   private final ProjectService projectService;
  
   private final EquipmentService equipmentService;
   private final EmployeeAssignmentMapper assignmentMapper;
   private final ApiResponseBuilder responseBuilder;
   private final EmployeeMapper employeeMapper;

   @Override
   public ResponseEntity<EmployeeAssignmentListResponse> getAllEmployeesAssignments() throws Exception {
       // TODO Auto-generated method stub
       List<EmployeeAssignmentDto> employeeAssignmentDtosList =assignmentMapper.toDtoList(assignmentService.findAll());
       EmployeeAssignmentListResponse employeeAssignmentListResponse= assignmentMapper.toEmployeeAssignmentListResponse(responseBuilder.buildSuccessApiResponse("Employee Assignment build successfully"));
       employeeAssignmentListResponse.data(employeeAssignmentDtosList);
       return ResponseEntity.ok(employeeAssignmentListResponse);
   }

   @Override
   public ResponseEntity<EmployeeAssignmentResponse> getEmployeeAssignmentById(Long id) throws Exception {
    // TODO Auto-generated method stub
    EmployeeAssignmentDto employeeAssignmentDto= assignmentMapper.toDto(assignmentService.findById(id).orElseThrow(() -> new Exception("Employee Assignment not found")));
    EmployeeAssignmentResponse employeeAssignmentResponse=assignmentMapper.toEmployeeAssignmentResponse(responseBuilder.buildSuccessApiResponse("Employee Assignment build by ID successfull"));
    employeeAssignmentResponse.data(employeeAssignmentDto);
    return ResponseEntity.ok(employeeAssignmentResponse);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> createEmployeeAssignment(@Valid EmployeeAssignmentRequestDto employeeAssignmentRequestDto)
        throws Exception {
    // TODO Auto-generated method stub
    Equipment equipment=equipmentService.findById(employeeAssignmentRequestDto.getEquipmentId()).orElseThrow(() ->new Exception("Equipment not found"));
    Project project=projectService.findById(employeeAssignmentRequestDto.getProjectId()).orElseThrow(() ->new Exception("Project not found"));
    Employee employee=employeeService.findById(employeeAssignmentRequestDto.getEmployeeId()).orElseThrow(() ->new Exception("Employee not found"));
    assignmentService.save(assignmentMapper.fromDtoWithReferences(employeeAssignmentRequestDto, employee, project, equipment));
    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= assignmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Employee Assignment added successfully"));
    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteEmployeeAssignment(Long id) throws Exception {
    // TODO Auto-generated method stub
    assignmentService.deleteById(id);
    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=assignmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Employee Assignment deleted successfully"));
    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateEmployeeAssigment(Long id,
        @Valid EmployeeAssignmentDto employeeAssignmentDto) throws Exception {
    // TODO Auto-generated method stub
    EmployeeAssignment existingemployeeAssignment=assignmentService.findById(id).orElseThrow(() -> new Exception("Employee Assignment not found"));
    assignmentMapper.updateEntityFromDto(employeeAssignmentDto, existingemployeeAssignment);
    EmployeeAssignment updatedEmployeeAssignment= assignmentService.save(existingemployeeAssignment);
    MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= assignmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Employee Assignment details updated successfully"));
    return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }




   

//    @Override
//    public ResponseEntity<Object> createEmployee(@Valid EmployeeRequestDto employeeRequestDto) throws Exception {
//     // TODO Auto-generated method stub
//     Employee employee=employeeMapper.toEntity(employeeRequestDto);
//     Employee employeesaved= employeeService.save(employee);
//     EmployeeResponse employeeResponse= employeeMapper.toEmployeeResponse(responseBuilder.buildSuccessApiResponse("Employee Created successfully"));
//     return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
//    }


//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<EmployeeAssignmentDto>>> getAllAssignments(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<EmployeeAssignment> assignmentsPage = assignmentService.findAll(pageable);
//        List<EmployeeAssignmentDto> assignmentDtos = assignmentsPage.getContent().stream()
//                .map(assignmentMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(assignmentDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EmployeeAssignmentDto>> getAssignmentById(@PathVariable Long id) {
//        return assignmentService.findById(id)
//                .map(assignment -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(assignmentMapper.toDto(assignment))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Employee assignment not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<EmployeeAssignmentDto>> createAssignment(@Valid @RequestBody EmployeeAssignmentDto assignmentDto) {
//        Optional<Employee> employeeOpt = employeeService.findById(assignmentDto.getEmployeeId());
//        if (employeeOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Employee not found with id: " + assignmentDto.getEmployeeId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(assignmentDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + assignmentDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(assignmentDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + assignmentDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        EmployeeAssignment assignment = assignmentMapper.fromDtoWithReferences(
//                assignmentDto,
//                employeeOpt.get(),
//                projectOpt.get(),
//                equipmentOpt.get());

//        EmployeeAssignment savedAssignment = assignmentService.save(assignment);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                assignmentMapper.toDto(savedAssignment),
//                "Employee assignment created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EmployeeAssignmentDto>> updateAssignment(
//            @PathVariable Long id,
//            @Valid @RequestBody EmployeeAssignmentDto assignmentDto) {
//        if (!assignmentService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Employee assignment not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Employee> employeeOpt = employeeService.findById(assignmentDto.getEmployeeId());
//        if (employeeOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Employee not found with id: " + assignmentDto.getEmployeeId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Project> projectOpt = projectService.findById(assignmentDto.getProjectId());
//        if (projectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + assignmentDto.getProjectId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Equipment> equipmentOpt = equipmentService.findById(assignmentDto.getEquipmentId());
//        if (equipmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Equipment not found with id: " + assignmentDto.getEquipmentId(),
//                    ErrorType.NOT_FOUND));
//        }

//        EmployeeAssignment existingAssignment = assignmentService.findById(id).get();
//        assignmentMapper.updateEntityFromDto(assignmentDto, existingAssignment);
//        existingAssignment.setEmployee(employeeOpt.get());
//        existingAssignment.setProject(projectOpt.get());
//        existingAssignment.setEquipment(equipmentOpt.get());

//        EmployeeAssignment updatedAssignment = assignmentService.save(existingAssignment);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                assignmentMapper.toDto(updatedAssignment),
//                "Employee assignment updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteAssignment(@PathVariable Long id) {
//        if (!assignmentService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Employee assignment not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        assignmentService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Employee assignment deleted successfully"));
//    }
}

