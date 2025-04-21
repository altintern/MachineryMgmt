package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.ProjectDto;
import com.machinarymgmt.service.dto.ProjectListResponse;
import com.machinarymgmt.service.dto.ProjectRequestDto;
import com.machinarymgmt.service.dto.ProjectResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper extends MachinaryMgmtMapper {

    ProjectDto toDto(Project project);

    ProjectRequestDto toRequestDto(Project project);
    
    List<ProjectDto> toDtoList(List<Project> projects);

    Project toEntity(ProjectRequestDto dto);

    void updateEntityFromDto(ProjectRequestDto dto, @MappingTarget Project project);

    ProjectListResponse toProjectListResponse(BaseApiResponse baseApiResponse);

    ProjectResponse toProjectResponse(BaseApiResponse baseApiResponse);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

