package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.api.data.model.StockStatement;
import com.machinarymgmt.service.dto.StockStatementListResponse;
import com.machinarymgmt.service.dto.StockStatementResponse;
import com.machinarymgmt.service.dto.StockStatementRequestDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.StockStatementDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ProjectMapper.class, ItemMapper.class, EquipmentMapper.class}
)
public interface StockStatementMapper extends MachinaryMgmtMapper {

    // @Mapping(source = "item.id", target = "itemId")
    // @Mapping(source = "item.name", target = "itemName")
    // @Mapping(source = "project.id", target = "projectId")
    // @Mapping(source = "project.name", target = "projectName")
    // @Mapping(source = "equipment.id", target = "equipmentId")
    // @Mapping(source = "equipment.name", target = "equipmentName")


    StockStatementDto toDto(StockStatement stockStatement);

    //StockStatementRequestDto toDto(StockStatement stockStatement);
    
    List<StockStatementDto> toDtoList(List<StockStatement> stockStatements);

    StockStatement toEntity(StockStatementRequestDto dto);

    StockStatement toEntity(StockStatementDto dto);

    void updateEntityFromDto(StockStatementRequestDto dto, @MappingTarget StockStatement stockStatement);

    StockStatementListResponse toStockStatementListResponse(BaseApiResponse baseApiResponse);

    StockStatementResponse toStockStatementResponse(BaseApiResponse baseApiResponse);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
    
    default StockStatement fromDtoWithReferences(
            StockStatementRequestDto dto,
            Project project,
            Item item,
            Equipment equipment) {
        StockStatement stockStatement = toEntity(dto);
        stockStatement.setProject(project);
        stockStatement.setItem(item);
        stockStatement.setEquipment(equipment);
        return stockStatement;
    }


}
