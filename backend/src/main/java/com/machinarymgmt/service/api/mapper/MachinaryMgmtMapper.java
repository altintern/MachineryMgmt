package com.machinarymgmt.service.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Base mapper interface for the Machinery Management application.
 * This will be extended by specific entity mappers.
 */
@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MachinaryMgmtMapper {
    // Base mapper methods will be added here
}

