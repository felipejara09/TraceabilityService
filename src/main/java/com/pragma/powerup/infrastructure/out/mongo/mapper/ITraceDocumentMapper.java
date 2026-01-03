package com.pragma.powerup.infrastructure.out.mongo.mapper;

import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.infrastructure.out.mongo.document.TraceLogDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ITraceDocumentMapper {

    TraceLogDocument toDocument(TraceLog log);
    TraceLog toModel(TraceLogDocument d);
}