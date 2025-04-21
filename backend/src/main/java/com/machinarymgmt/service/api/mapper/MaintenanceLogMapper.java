package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.MachineryMaintenanceLog;
import com.machinarymgmt.service.api.data.model.MaintenancePartsUsed;
import com.machinarymgmt.service.api.data.model.MaintenanceReading;
import com.machinarymgmt.service.dto.MaintenanceLogListResponse;
import com.machinarymgmt.service.dto.MaintenanceLogResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.MaintenanceLogDto;
import com.machinarymgmt.service.dto.MaintenanceLogRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {EquipmentMapper.class, MaintenancePartUsedMapper.class, MaintenanceReadingMapper.class}
)
public interface MaintenanceLogMapper extends MachinaryMgmtMapper {

    MaintenanceLogDto toDto(MachineryMaintenanceLog log);
    
    List<MaintenanceLogDto> toDtoList(List<MachineryMaintenanceLog> logs);

    MachineryMaintenanceLog toEntity(MaintenanceLogRequestDto dto);

    void updateEntityFromDto(MaintenanceLogRequestDto dto, @MappingTarget MachineryMaintenanceLog log);

    MaintenanceLogListResponse toMaintenanceLogListResponse(BaseApiResponse baseApiResponse);

    MaintenanceLogResponse toMaintenanceLogResponse(BaseApiResponse baseApiResponse);
    
    default MachineryMaintenanceLog fromDtoWithReferences(
            MaintenanceLogRequestDto dto, 
            Equipment equipment, 
            List<MaintenancePartsUsed> partsUsed,
            MaintenanceReading readings) {
        MachineryMaintenanceLog log = toEntity(dto);
        log.setEquipment(equipment);
        log.setPartsUsed(partsUsed);
        log.setReadings(readings);
        return log;
    }

    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

