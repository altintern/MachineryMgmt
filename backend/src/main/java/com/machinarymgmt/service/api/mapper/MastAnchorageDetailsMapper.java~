// package com.machinarymgmt.service.api.mapper;
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
// import com.machinarymgmt.service.api.data.model.Equipment;
// import com.machinarymgmt.service.api.data.model.MastAnchorageDetails;
// import com.machinarymgmt.service.api.data.model.Project;
// import com.machinarymgmt.service.dto.MastAnchorageDetailsDto;
// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
// import org.mapstruct.MappingTarget;
// import org.mapstruct.ReportingPolicy;

// import java.util.List;

// @Mapper(
//     componentModel = "spring",
//     unmappedTargetPolicy = ReportingPolicy.IGNORE,
//     uses = {ProjectMapper.class, EquipmentMapper.class}
// )
// public interface MastAnchorageDetailsMapper extends MachinaryMgmtMapper {

//     MastAnchorageDetailsDto toDto(MastAnchorageDetails details);
    
//     List<MastAnchorageDetailsDto> toDtoList(List<MastAnchorageDetails> details);

//     MastAnchorageDetails toEntity(MastAnchorageDetailsDto dto);

    MastAnchorageDetails toEntity(MastAnchorageDetailsRequestDto dto);

    void updateEntityFromDto(MastAnchorageDetailsRequestDto dto, @MappingTarget MastAnchorageDetails details);

    MastAnchorageDetailsListResponse toMastAnchorageDetailsListResponse(BaseApiResponse baseApiResponse);

    MastAnchorageDetailsResponse toMastAnchorageDetailsResponse(BaseApiResponse baseApiResponse);
    
    
    default MastAnchorageDetails fromDtoWithReferences(
            MastAnchorageDetailsRequestDto dto,
            Project project,
            Equipment equipment) {
        MastAnchorageDetails details = toEntity(dto);
        details.setProject(project);
        details.setEquipment(equipment);
        return details;
    }
    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

//     void updateEntityFromDto(MastAnchorageDetailsDto dto, @MappingTarget MastAnchorageDetails details);
    
//     default MastAnchorageDetails fromDtoWithReferences(
//             MastAnchorageDetailsDto dto,
//             Project project,
//             Equipment equipment) {
//         MastAnchorageDetails details = toEntity(dto);
//         details.setProject(project);
//         details.setEquipment(equipment);
//         return details;
//     }
// }
