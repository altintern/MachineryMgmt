package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.dto.MaintenancePartUsedListResponse;
import com.machinarymgmt.service.dto.MaintenancePartUsedRequestDto;
import com.machinarymgmt.service.dto.MaintenancePartUsedResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MaintenanceLogRequestDto;
import com.machinarymgmt.service.dto.MaintenancePartUsedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ItemMapper.class}
)
public interface MaintenancePartUsedMapper extends MachinaryMgmtMapper {

    MaintenancePartUsedDto toDto(MaintenancePartsUsed partsUsed);
    
    List<MaintenancePartUsedDto> toDtoList(List<MaintenancePartsUsed> partsUsed);

    MaintenancePartsUsed toEntity(MaintenancePartUsedRequestDto dto);

    MaintenancePartUsedListResponse toMaintenancePartUsedListResponse(BaseApiResponse baseApiResponse);

    MaintenancePartUsedResponse toMaintenancePartUsedResponse(BaseApiResponse baseApiResponse);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);

    void updateEntityFromDto(MaintenancePartUsedRequestDto dto, @MappingTarget MachineryMaintenanceLog log);
    
    default MaintenancePartsUsed fromDtoWithReferences(
            MaintenancePartUsedRequestDto dto,
            MachineryMaintenanceLog log,
            Item item) {
        MaintenancePartsUsed partsUsed = toEntity(dto);
        partsUsed.setMaintenanceLog(log);
        partsUsed.setItem(item);
        partsUsed.setQuantity(BigDecimal.valueOf(dto.getQuantity()));
        return partsUsed;
    }
    
    default Double mapBigDecimalToDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }
    
    default BigDecimal mapDoubleToDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
}

