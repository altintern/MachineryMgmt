package com.machinarymgmt.service.api.mapper;
//updated
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.*;
import com.machinarymgmt.service.dto.IncidentReportDto;
import com.machinarymgmt.service.dto.IncidentReportListResponse;
import com.machinarymgmt.service.dto.IncidentReportRequestDto;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EquipmentMapper.class, ProjectMapper.class}
)
public interface IncidentReportMapper extends MachinaryMgmtMapper {

    IncidentReportDto toDto(IncidentReport incidentReport);

    List<IncidentReportDto> toDtoList(List<IncidentReport> incidentReports);

    IncidentReport toEntity(IncidentReportRequestDto dto);
    
    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);

    void updateIncidentReportFromDto(IncidentReportRequestDto dto, @MappingTarget IncidentReport incidentReport);

    void updateEntityFromDto(IncidentReportRequestDto dto, @MappingTarget IncidentReport incidentReport);

    default IncidentReport fromDtoWithReferences(
            IncidentReportRequestDto dto,
            Equipment equipment,
            Project project) {
        IncidentReport incidentReport = toEntity(dto);
        incidentReport.setEquipment(equipment);
        incidentReport.setProject(project);
        return incidentReport;
    }

    // ✅ Custom mapping methods inline
    default OffsetDateTime map(LocalDate date) {
        return date != null ? date.atStartOfDay().atOffset(ZoneOffset.UTC) : null;
    }

    default LocalDate map(OffsetDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }

//    default String map(StatusEntity status) {
//        return status != null ? status.getName() : null;
//    }
//
//    default StatusEntity map(String statusName) {
//        if (statusName == null) return null;
//        StatusEntity entity = new StatusEntity();
//        entity.setName(statusName);
//        return entity;
//    }

    IncidentReportListResponse toDtoList(BaseApiResponse successApiResponse);

}


