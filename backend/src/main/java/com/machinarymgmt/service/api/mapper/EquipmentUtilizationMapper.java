package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentUtilization;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EquipmentUtilizationListResponse;
import com.machinarymgmt.service.dto.EquipmentUtilizationResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.EquipmentUtilizationDto;
import com.machinarymgmt.service.dto.EquipmentUtilizationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {EquipmentMapper.class, ProjectMapper.class}
)
public interface EquipmentUtilizationMapper extends MachinaryMgmtMapper {

    EquipmentUtilizationDto toDto(EquipmentUtilization utilization);
    
    List<EquipmentUtilizationDto> toDtoList(List<EquipmentUtilization> utilizations);

    EquipmentUtilization toEntity(EquipmentUtilizationDto dto);

    EquipmentUtilization toEntity(EquipmentUtilizationRequestDto dto);

    void updateEntityFromDto(EquipmentUtilizationDto dto, @MappingTarget EquipmentUtilization utilization);

    EquipmentUtilizationListResponse toEquipmentUtilizationListResponse(BaseApiResponse baseApiResponse);

    EquipmentUtilizationResponse toEquipmentUtilizationResponse(BaseApiResponse baseApiResponse);
    
    default EquipmentUtilization fromDtoWithReferences(
            EquipmentUtilizationDto dto,
            Equipment equipment,
            Project project) {
        EquipmentUtilization utilization = toEntity(dto);
        utilization.setEquipment(equipment);
        utilization.setProject(project);
        return utilization;
    }
    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

