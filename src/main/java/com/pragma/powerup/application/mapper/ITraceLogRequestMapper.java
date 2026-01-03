package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.RegisterTraceLogRequestDto;
import com.pragma.powerup.domain.model.TraceLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITraceLogRequestMapper {
    TraceLog toModel(RegisterTraceLogRequestDto dto);
}
