package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MaintenanceLogRequestDto;
import com.machinarymgmt.service.dto.MaintenancePartUsedDto;
import com.machinarymgmt.service.dto.MaintenanceReadingListResponse;
import com.machinarymgmt.service.dto.MaintenanceReadingResponse;
import com.machinarymgmt.service.dto.MaintenanceReadingDto;
import com.machinarymgmt.service.dto.MaintenanceReadingRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;
import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MaintenanceReadingMapper extends MachinaryMgmtMapper {
    
    MaintenanceReadingDto toDto(MaintenanceReading reading);

    MaintenanceReading toEntity(MaintenanceReadingRequestDto dto);

    List<MaintenanceReadingDto> toDtoList(List<MaintenanceReading> readings);

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);

    MaintenanceReadingResponse toMaintenanceReadingResponse(BaseApiResponse baseApiResponse);

    MaintenanceReadingListResponse toMaintenancereadingListResponse(BaseApiResponse baseApiResponse);

    void updateEntityFromDto(MaintenanceReadingRequestDto dto, @MappingTarget MaintenanceReading log);

    default MaintenanceReading fromDtoWithReferences(
            MaintenanceReadingRequestDto dto,
            MachineryMaintenanceLog log) {
        if (dto == null) {
            return null;
        }
        MaintenanceReading reading = toEntity(dto);
        reading.setMaintenanceLog(log);
        return reading;
    }
    
    default Double mapBigDecimalToDouble(BigDecimal value) {
        return value != null ? value.doubleValue() : null;
    }
    
    default BigDecimal mapDoubleToDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : null;
    }
}

