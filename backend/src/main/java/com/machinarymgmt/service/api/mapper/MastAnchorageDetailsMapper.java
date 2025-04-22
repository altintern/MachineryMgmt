package com.machinarymgmt.service.api.mapper;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.MastAnchorageDetailsResponse;
import com.machinarymgmt.service.dto.MastAnchorageDetailsResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MastAnchorageDetailsDto;
import com.machinarymgmt.service.dto.MastAnchorageDetailsRequestDto;
import com.machinarymgmt.service.dto.MastAnchorageDetailsListResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
 import com.machinarymgmt.service.api.data.model.Equipment;
 import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
 import com.machinarymgmt.service.api.data.model.Project;
 import com.machinarymgmt.service.dto.MastAnchorageDetailsDto;
 import org.mapstruct.Mapper;
 import org.mapstruct.Mapping;
 import org.mapstruct.MappingTarget;
 import org.mapstruct.ReportingPolicy;

 import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ProjectMapper.class, EquipmentMapper.class}
)
public interface MastAnchorageDetailsMapper extends MachinaryMgmtMapper {

    // Convert entity to DTO
    MastAnchorageDetailsDto toDto(MastAnchorageDetails details);

    List<MastAnchorageDetailsDto> toDtoList(List<MastAnchorageDetails> details);

    // Convert DTO to entity, ignoring project and equipment
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "equipment", ignore = true)
    MastAnchorageDetails toEntity(MastAnchorageDetailsRequestDto dto);

    // Convert DTO to entity with project and equipment references
    default MastAnchorageDetails fromDtoWithReferences(
            MastAnchorageDetailsRequestDto dto,
            Project project,
            Equipment equipment) {
        MastAnchorageDetails details = toEntity(dto);
        details.setProject(project);
        details.setEquipment(equipment);
        return details;
    }

    // Response mapping
    MastAnchorageDetailsListResponse toMastAnchorageDetailsListResponse(BaseApiResponse baseApiResponse);
    MastAnchorageDetailsResponse toMastAnchorageDetailsResponse(BaseApiResponse baseApiResponse);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);

    // Update entity from DTO
    void updateEntityFromDto(MastAnchorageDetailsRequestDto dto, @MappingTarget MastAnchorageDetails details);

}

