package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.EmployeesApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;

import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.dto.EmployeeDto;

import com.machinarymgmt.service.api.mapper.EmployeeMapper;
import com.machinarymgmt.service.api.service.DepartmentService;
import com.machinarymgmt.service.api.service.DesignationService;
import com.machinarymgmt.service.api.service.EmployeeService;

import com.machinarymgmt.service.dto.DepartmentDto;
import com.machinarymgmt.service.dto.DepartmentListResponse;
import com.machinarymgmt.service.dto.EmployeeListResponse;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import org.springframework.http.HttpStatus;

import com.machinarymgmt.service.dto.EmployeeDto;
//import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL )
public class EmployeeApiController implements EmployeesApi {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final EmployeeMapper employeeMapper;
    private final ApiResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<EmployeeListResponse> getAllEmployees() throws Exception {
        // TODO Auto-generated method stub
        List<EmployeeDto> employeeDtosList =employeeMapper.toDtoList(employeeService.findAll());
        EmployeeListResponse employeeListResponse= employeeMapper.toEmployeeListResponse(responseBuilder.buildSuccessApiResponse("Employee build successfully"));
        employeeListResponse.data(employeeDtosList);
        return ResponseEntity.ok(employeeListResponse);
    }

    @Override
    public ResponseEntity<EmployeeResponse> getEmployeeById(Long id) throws Exception {
        // TODO Auto-generated method stub
        EmployeeDto employeeDto= employeeMapper.toDto(employeeService.findById(id).orElseThrow(() -> new Exception("Employee not found")));
        EmployeeResponse employeeResponse= employeeMapper.toEmployeeResponse(responseBuilder.buildSuccessApiResponse("Employee Build by ID successfull"));
        employeeResponse.data(employeeDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> createEmployee(EmployeeRequestDto employeeRequestDto) throws Exception {
        Department department = departmentService.findById(employeeRequestDto.getDepartmentId())
                .orElseThrow(() -> new Exception("Department not found"));
        Designation designation = designationService.findById(employeeRequestDto.getDesignationId())
                .orElseThrow(() -> new Exception("Designation not found"));
        employeeService.save(employeeMapper.fromDtoWithReferences(employeeRequestDto, designation, department));
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = employeeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("employee added succesfully"));
     return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }


    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateEmployee(Long id, @Valid EmployeeRequestDto employeeRequestDto) throws Exception {
      // TODO Auto-generated method stub
      Employee existingEmployee= employeeService.findById(id).orElseThrow(() -> new Exception("Employee not found"));
      employeeMapper.updateEntityFromDto(employeeRequestDto, existingEmployee);
      Employee updatedEmployee= employeeService.save(existingEmployee);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=employeeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Employee details Updated successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteEmployee(Long id) throws Exception {
      // TODO Auto-generated method stub
      employeeService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=employeeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Employee details deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }

}


// @Override
// public ResponseEntity<Object> createProject(@Valid ProjectRequestDto projectRequestDto) throws Exception {
//    Project project = projectMapper.toEntity(projectRequestDto);
//     Project projectsaved = projectService.save(project);
//     ProjectResponse projectResponse = projectMapper.toProjectResponse(responseBuilder.buildSuccessApiResponse("project created succesfulkly"));
//     return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
// }

//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<EmployeeDto>>> getAllEmployees(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<Employee> employeesPage = employeeService.findAll(pageable);
//        List<EmployeeDto> employeeDtos = employeesPage.getContent().stream()
//                .map(employeeMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(employeeDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EmployeeDto>> getEmployeeById(@PathVariable Long id) {
//        return employeeService.findById(id)
//                .map(employee -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(employeeMapper.toDto(employee))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Employee not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<EmployeeDto>> createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
//        Optional<Department> departmentOpt = departmentService.findById(employeeDto.getDeptId());
//        if (departmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Department not found with id: " + employeeDto.getDeptId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Designation> designationOpt = designationService.findById(employeeDto.getDesignationId());
//        if (designationOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Designation not found with id: " + employeeDto.getDesignationId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Employee employee = employeeMapper.fromDtoWithReferences(
//                employeeDto,
//                designationOpt.get(),
//                departmentOpt.get());

//        Employee savedEmployee = employeeService.save(employee);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                employeeMapper.toDto(savedEmployee),
//                "Employee created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EmployeeDto>> updateEmployee(
//            @PathVariable Long id,
//            @Valid @RequestBody EmployeeDto employeeDto) {
//        if (!employeeService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Employee not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Department> departmentOpt = departmentService.findById(employeeDto.getDeptId());
//        if (departmentOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Department not found with id: " + employeeDto.getDeptId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Designation> designationOpt = designationService.findById(employeeDto.getDesignationId());
//        if (designationOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Designation not found with id: " + employeeDto.getDesignationId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Employee existingEmployee = employeeService.findById(id).get();
//        employeeMapper.updateEntityFromDto(employeeDto, existingEmployee);
//        existingEmployee.setDepartment(departmentOpt.get());
//        existingEmployee.setDesignation(designationOpt.get());

//        Employee updatedEmployee = employeeService.save(existingEmployee);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                employeeMapper.toDto(updatedEmployee),
//                "Employee updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
//        if (!employeeService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Employee not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        employeeService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Employee deleted successfully"));
//    }


