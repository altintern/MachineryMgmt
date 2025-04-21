package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.dto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemMapper extends MachinaryMgmtMapper {

    ItemDto toDto(Item item);
    
    List<ItemDto> toDtoList(List<Item> items);

    Item toEntity(ItemDto dto);
}

