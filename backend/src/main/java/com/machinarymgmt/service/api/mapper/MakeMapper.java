package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MakeMapper extends MachinaryMgmtMapper {

    MakeDto toDto(Make make);
    
    List<MakeDto> toDtoList(List<Make> makes);

    Make toEntity(MakeRequestDto dto);

    MakeListResponse toDtoList(BaseApiResponse baseApiResponse);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);

    MakeResponse toMakeApiResponse(BaseApiResponse baseApiResponse);

    void updateMakeFromDto(MakeRequestDto dto, @MappingTarget Make make);

}

