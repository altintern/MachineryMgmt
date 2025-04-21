package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.DepartmentsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.mapper.DepartmentMapper;
import com.machinarymgmt.service.api.service.DepartmentService;
import com.machinarymgmt.service.dto.DepartmentListResponse;
import com.machinarymgmt.service.dto.DepartmentRequestDto;
import com.machinarymgmt.service.dto.DepartmentResponse;
import com.machinarymgmt.service.dto.EmployeeDto;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
//import com.machinarymgmt.service.dto.BaseApiResponse;
import com.machinarymgmt.service.dto.ProjectDto;
import com.machinarymgmt.service.dto.ProjectRequestDto;
import com.machinarymgmt.service.dto.ProjectResponse;

import jakarta.validation.Valid;

import com.machinarymgmt.service.dto.DepartmentDto;
import com.machinarymgmt.service.dto.DepartmentListResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL )
public class DepartmentController implements DepartmentsApi{

   private final DepartmentService departmentService;
   private final DepartmentMapper departmentMapper;
   private final ApiResponseBuilder responseBuilder;

   @Override
   public ResponseEntity<DepartmentListResponse> getAllDepartments() throws Exception {
    // TODO Auto-generated method stub
    List<DepartmentDto> departmentDtosList= departmentMapper.toDtoList(departmentService.findAll());
    DepartmentListResponse departmentListResponse= departmentMapper.toDepartmentListResponse(responseBuilder.buildSuccessApiResponse("Department Build successfully"));
    departmentListResponse.data(departmentDtosList);
    return ResponseEntity.ok(departmentListResponse);

   }

   @Override
   public ResponseEntity<DepartmentResponse> getDepartmentById(Long id) throws Exception {
    // TODO Auto-generated method stub
    DepartmentDto departmentDto= departmentMapper.toDto(departmentService.findById(id).orElseThrow(() -> new Exception("Department not found")));
    DepartmentResponse departmentResponse=departmentMapper.toDepartmentResponse(responseBuilder.buildSuccessApiResponse("Department build by ID successfull"));
    departmentResponse.data(departmentDto);
    return ResponseEntity.ok(departmentResponse);
   }


   @Override
   public ResponseEntity<Object> createDepartment(@Valid DepartmentRequestDto departmentRequestDto) throws Exception {
    // TODO Auto-generated method stub
    Department department= departmentMapper.toEntity(departmentRequestDto);
            Department departmentsaved= departmentService.save(department);
            DepartmentResponse departmentResponse=departmentMapper.toDepartmentResponse(responseBuilder.buildSuccessApiResponse("Department Created successfully"));
    // TODO Auto-generated method stub
    return new ResponseEntity<>(departmentResponse, HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateDepartment(Long id, @Valid DepartmentRequestDto departmentRequestDto)
                throws Exception {
        // TODO Auto-generated method stub
        Department existingDepartment= departmentService.findById(id).orElseThrow(() -> new Exception("Department not found"));
        departmentMapper.updateEntityFromDto(departmentRequestDto, existingDepartment);
        Department updatedDepartment= departmentService.save(existingDepartment);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=departmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Department details updated successfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }

   


    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteDepartment(Long id) throws Exception {
        // TODO Auto-generated method stub
        departmentService.deleteById(id);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= departmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Department deleted successfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
}



// @Override
//    public ResponseEntity<Object> createProject(@Valid ProjectRequestDto projectRequestDto) throws Exception {
//       Project project = projectMapper.toEntity(projectRequestDto);
//        Project projectsaved = projectService.save(project);
//        ProjectResponse projectResponse = projectMapper.toProjectResponse(responseBuilder.buildSuccessApiResponse("project created succesfulkly"));
//        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
//    }
//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<Department>>> getAllDepartments() {
//        List<Department> departments = departmentService.findAll();
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(departments));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Department>> getDepartmentById(@PathVariable Long id) {
//        return departmentService.findById(id)
//                .map(department -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(department)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Department not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<Department>> createDepartment(@RequestBody Department department) {
//        if (departmentService.existsByName(department.getName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Department already exists with name: " + department.getName(),
//                    ErrorType.DUPLICATE));
//        }
//        Department savedDepartment = departmentService.save(department);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(savedDepartment, "Department created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Department>> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
//        if (!departmentService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Department not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        department.setId(id);
//        Department updatedDepartment = departmentService.save(department);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(updatedDepartment, "Department updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
//        if (!departmentService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Department not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        departmentService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Department deleted successfully"));
//    }
}

