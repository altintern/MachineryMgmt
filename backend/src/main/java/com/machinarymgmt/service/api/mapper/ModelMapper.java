package com.machinarymgmt.service.api.mapper;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.dto.ModelDto;
import com.machinarymgmt.service.dto.ModelListResponse;
import com.machinarymgmt.service.dto.ModelRequestDto;
import com.machinarymgmt.service.dto.ModelResponse;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {MakeMapper.class}
)
public interface ModelMapper extends MachinaryMgmtMapper {

    ModelDto toDto(Model model);
    
    List<ModelDto> toDtoList(List<Model> models);

    ModelRequestDto toRequestDto(Model model);

    Model toEntity(ModelDto dto);

    Model toEntity(ModelRequestDto requestDto);

    void updateEntityFromDto(ModelRequestDto dto, @MappingTarget Model model);
    
    default Model fromDtoWithReferences(ModelDto dto, Make make) {
        Model model = toEntity(dto);
        model.setMake(make);
        return model;
    }

    ModelListResponse toDtoList(BaseApiResponse baseApiResponse); 
    ModelResponse toModelResponse(BaseApiResponse baseApiResponse);  
}

