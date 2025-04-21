package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.dto.EmployeeRequestDto;
import com.machinarymgmt.service.dto.EquipmentCategoryDto;
import com.machinarymgmt.service.dto.EquipmentCategoryRequestDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.EquipmentCategoryListResponse;
import com.machinarymgmt.service.dto.EquipmentCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EquipmentCategoryMapper extends MachinaryMgmtMapper {

   
    EquipmentCategoryDto toDto(EquipmentCategory category);
    
    List<EquipmentCategoryDto> toDtoList(List<EquipmentCategory> categories);

    EquipmentCategory toEntity(EquipmentCategoryRequestDto dto);

     MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);


    EquipmentCategoryListResponse toDtoList(BaseApiResponse baseApiResponse);
    
    EquipmentCategoryResponse toCategoryResponse(BaseApiResponse baseApiResponse);

    void updateEntityFromDto(EquipmentCategoryRequestDto dto, @MappingTarget EquipmentCategory equipmentCategory);

    
}

