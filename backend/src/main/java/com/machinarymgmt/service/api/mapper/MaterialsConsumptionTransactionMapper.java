package com.machinarymgmt.service.api.mapper;

import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Equipment;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.data.model.MaterialsConsumptionTransaction;
import com.machinarymgmt.service.api.data.model.Project;
import com.machinarymgmt.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {ProjectMapper.class, EquipmentMapper.class, ItemMapper.class}
)
public interface MaterialsConsumptionTransactionMapper extends MachinaryMgmtMapper {

    MaterialsConsumptionTransactionDto toDto(MaterialsConsumptionTransaction transaction);

    List<MaterialsConsumptionTransactionDto> toDtoList(List<MaterialsConsumptionTransaction> transactions);

    MaterialsConsumptionTransaction toEntity(MaterialsConsumptionTransactionRequest request);

    void updateEntityFromDto(MaterialsConsumptionTransactionRequest request, @MappingTarget MaterialsConsumptionTransaction transaction);

    default MaterialsConsumptionTransaction fromDtoWithReferences(
            MaterialsConsumptionTransactionRequest request,
            Project project,
            Equipment equipment,
            Item item) {
        MaterialsConsumptionTransaction transaction = toEntity(request);
        transaction.setProject(project);
        transaction.setEquipment(equipment);
        transaction.setItem(item);
        return transaction;
    }
    MaterialsConsumptionTransactionListResponse toListResponse(BaseApiResponse baseApiResponse);
    MachinaryMgmtBaseApiResponse toDtoResponse(BaseApiResponse baseApiResponse);
    MaterialsConsumptionTransactionResponse toResponse(BaseApiResponse baseApiResponse);
}

