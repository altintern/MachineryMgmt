package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Department;
import com.machinarymgmt.service.api.data.model.Designation;
import com.machinarymgmt.service.dto.DesignationListResponse;
import com.machinarymgmt.service.dto.DesignationResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.DesignationRequestDto;
import com.machinarymgmt.service.dto.DesignationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DesignationMapper extends MachinaryMgmtMapper {

    DesignationDto toDto(Designation designation);
    
    List<DesignationDto> toDtoList(List<Designation> designations);

    DesignationRequestDto toDesignationRequestDto(Designation designation);

    Designation toEntity(DesignationRequestDto dto);

    void updateEntityFromDto(DesignationRequestDto dto, @MappingTarget Designation designation);

    DesignationListResponse toDesignationListResponse(BaseApiResponse baseApiResponse);

    DesignationResponse toDesignationResponse(BaseApiResponse baseApiResponse);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

