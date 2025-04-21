package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.EquipmentListResponse;
import com.machinarymgmt.service.dto.EquipmentResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.EquipmentDto;
import com.machinarymgmt.service.dto.EquipmentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ProjectMapper.class, EquipmentCategoryMapper.class, ModelMapper.class}
)
public interface EquipmentMapper extends MachinaryMgmtMapper {

    EquipmentDto toDto(Equipment equipment);
    
    List<EquipmentDto> toDtoList(List<Equipment> equipmentList);

    Equipment toEntity(EquipmentRequestDto dto);

    void updateEntityFromDto(EquipmentRequestDto dto, @MappingTarget Equipment equipment);

    EquipmentListResponse toEquipmentListResponse(BaseApiResponse baseApiResponse);

    EquipmentResponse toEquipmentResponse(BaseApiResponse baseApiResponse);
    
    default Equipment fromDtoWithReferences(
        EquipmentRequestDto dto, 
        EquipmentCategory category, 
        Model model,
        Project project
        ) {
        Equipment equipment = toEntity(dto);
        equipment.setProject(project);
        equipment.setCategory(category);
        equipment.setModel(model);
        return equipment;
    }
    MachinaryMgmtBaseApiResponse toBaseApiResponse(BaseApiResponse baseApiResponse);
}
// default Employee fromDtoWithReferences(
//     EmployeeRequestDto dto,
//     Designation designation,
//     Department department) {
// Employee employee = toEntity(dto);
// employee.setDesignation(designation);
// employee.setDepartment(department);
// return employee;
// }
