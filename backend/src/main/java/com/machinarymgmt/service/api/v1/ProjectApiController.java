package com.machinarymgmt.service.api.v1;
import com.machinarymgmt.service.api.ProjectsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.EmployeeAssignment;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.StockStatement;
import com.machinarymgmt.service.api.mapper.ProjectMapper;
import com.machinarymgmt.service.api.service.ProjectService;
import com.machinarymgmt.service.dto.DepartmentRequestDto;
import com.machinarymgmt.service.dto.DepartmentResponse;
import com.machinarymgmt.service.dto.EmployeeAssignmentDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.ProjectDto;
import com.machinarymgmt.service.dto.ProjectListResponse;
import com.machinarymgmt.service.dto.ProjectRequestDto;
import com.machinarymgmt.service.dto.ProjectResponse;
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

import static com.machinarymgmt.service.api.utils.Constants.PROJECTAPI_URL;
import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class ProjectApiController implements ProjectsApi {

   private final ProjectService projectService;
   private final ProjectMapper projectMapper;
   private final ApiResponseBuilder responseBuilder;
   @Override
   public ResponseEntity<ProjectListResponse> getAllProjects() throws Exception {
    // TODO Auto-generated method stub
    List<ProjectDto> projectDtosList = projectMapper.toDtoList(projectService.findAll());
    ProjectListResponse projectListResponse = projectMapper.toProjectListResponse(responseBuilder.buildSuccessApiResponse("Project build successfully"));
    projectListResponse.data(projectDtosList);
    return ResponseEntity.ok(projectListResponse);
   }
 

   @Override
   public ResponseEntity<ProjectResponse> getProjectById(Long id) throws Exception {
    ProjectDto projectDto = projectMapper.toDto(projectService.findById(id).orElseThrow(() -> new Exception("Project not found")));
    ProjectResponse projectResponse =projectMapper.toProjectResponse(responseBuilder.buildSuccessApiResponse("Project build by ID successfull"));
    projectResponse.data(projectDto);
    return ResponseEntity.ok(projectResponse);
   }


   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> updateProject(Long id, @Valid ProjectRequestDto projectRequestDto)
         throws Exception {
      // TODO Auto-generated method stub
      Project existingProject= projectService.findById(id).orElseThrow(() -> new Exception("Project Assignment not found"));
      projectMapper.updateEntityFromDto(projectRequestDto, existingProject);
      Project updatedProject= projectService.save(existingProject);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= projectMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Project details updated successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }


   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteProject(Long id) throws Exception {
      // TODO Auto-generated method stub
      projectService.deleteById(id);
      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse= projectMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Project deleted successfully"));
      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
   }
  
   @Override
   public ResponseEntity<ProjectResponse> createProject(@Valid ProjectRequestDto projectRequestDto)
         throws Exception {
      // TODO Auto-generated method stub
      Project project= projectMapper.toEntity(projectRequestDto);
      Project projectsaved= projectService.save(project);
      ProjectResponse projectResponse= projectMapper.toProjectResponse(responseBuilder.buildSuccessApiResponse("Project created successfully"));
      return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
   }

//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<ProjectDto>>> getAllProjects(
//            @RequestParam(required = false, defaultValue = "0") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer size,
//            Pageable pageable) {
//        Page<Project> projectPage = projectService.findAll(pageable);
//        List<ProjectDto> projectDtos = projectPage.getContent().stream()
//                .map(projectMapper::toDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(projectDtos));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<ProjectDto>> getProjectById(@PathVariable Long id) {
//        return projectService.findById(id)
//                .map(project -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(projectMapper.toDto(project))))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Project not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<ProjectDto>> createProject(@Valid @RequestBody ProjectRequestDto requestDto) {
//        if (projectService.existsByName(requestDto.getProjectName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project already exists with name: " + requestDto.getProjectName(),
//                    ErrorType.DUPLICATE));
//        }

//        Project project = projectMapper.toEntity(requestDto);
//        Project savedProject = projectService.save(project);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                projectMapper.toDto(savedProject),
//                "Project created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<ProjectDto>> updateProject(
//            @PathVariable Long id,
//            @Valid @RequestBody ProjectRequestDto requestDto) {
//        Optional<Project> existingProjectOpt = projectService.findById(id);
//        if (existingProjectOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Project existingProject = existingProjectOpt.get();
//        if (!existingProject.getName().equals(requestDto.getProjectName()) &&
//                projectService.existsByName(requestDto.getProjectName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project already exists with name: " + requestDto.getProjectName(),
//                    ErrorType.DUPLICATE));
//        }

//        projectMapper.updateEntityFromDto(requestDto, existingProject);
//        Project updatedProject = projectService.save(existingProject);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                projectMapper.toDto(updatedProject),
//                "Project updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteProject(@PathVariable Long id) {
//        if (!projectService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Project not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        projectService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Project deleted successfully"));
//    }
}

