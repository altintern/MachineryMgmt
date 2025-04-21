package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.DepartmentDto;
import com.machinarymgmt.service.dto.DepartmentListResponse;
import com.machinarymgmt.service.dto.DepartmentResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.DepartmentRequestDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DepartmentMapper extends MachinaryMgmtMapper {

    DepartmentDto toDto(Department department);

    DepartmentRequestDto toDepartmentRequestDto(Department department);
    
    List<DepartmentDto> toDtoList(List<Department> departments);
    

    Department toEntity(DepartmentRequestDto dto);

    void updateEntityFromDto(DepartmentDto dto, @MappingTarget Department department);

    void updateEntityFromDto(DepartmentRequestDto dto, @MappingTarget Department department);

    DepartmentListResponse toDepartmentListResponse(BaseApiResponse baseApiResponse);

    DepartmentResponse toDepartmentResponse(BaseApiResponse baseApiResponse);
    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

