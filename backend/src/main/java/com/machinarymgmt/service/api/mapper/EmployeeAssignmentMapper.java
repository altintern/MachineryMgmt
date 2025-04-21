package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Employee;
import com.machinarymgmt.service.api.data.model.EmployeeAssignment;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EmployeeAssignmentDto;
import com.machinarymgmt.service.dto.EmployeeAssignmentRequestDto;
import com.machinarymgmt.service.dto.EmployeeListResponse;
import com.machinarymgmt.service.dto.EmployeeResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.EmployeeAssignmentListResponse;
import com.machinarymgmt.service.dto.EmployeeAssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {EmployeeMapper.class, ProjectMapper.class, EquipmentMapper.class}
)
public interface EmployeeAssignmentMapper extends MachinaryMgmtMapper {

    EmployeeAssignmentDto toDto(EmployeeAssignment assignment);
    
    List<EmployeeAssignmentDto> toDtoList(List<EmployeeAssignment> assignments);

    EmployeeAssignment toEntity(EmployeeAssignmentDto dto);

    EmployeeAssignment toEntity(EmployeeAssignmentRequestDto dto);

    void updateEntityFromDto(EmployeeAssignmentRequestDto dto, @MappingTarget EmployeeAssignment assignment);

    
    EmployeeAssignmentListResponse toEmployeeAssignmentListResponse(BaseApiResponse baseApiResponse);

    EmployeeAssignmentResponse toEmployeeAssignmentResponse(BaseApiResponse baseApiResponse);
    
    default EmployeeAssignment fromDtoWithReferences(
            EmployeeAssignmentRequestDto dto,
            Employee employee,
            Project project,
            Equipment equipment) {
        EmployeeAssignment assignment = toEntity(dto);
        assignment.setEmployee(employee);
        assignment.setProject(project);
        assignment.setEquipment(equipment);
        return assignment;
    }
    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}

