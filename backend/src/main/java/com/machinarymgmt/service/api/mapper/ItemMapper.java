package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemMapper extends MachinaryMgmtMapper {

    @Mapping(source = "type", target = "itemType")
    ItemDto toDto(Item item);

    @Mapping(source = "itemType", target = "type")
    Item toEntity(ItemRequestDto dto);

    List<ItemDto> toDtoList(List<Item> item);
    ItemListResponse toDtoList(BaseApiResponse baseApiResponse);


    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);

    ItemResponse toItemApiResponse(BaseApiResponse baseApiResponse);

    void updateEntityFromDto(ItemRequestDto dto, @MappingTarget Item item);
}

