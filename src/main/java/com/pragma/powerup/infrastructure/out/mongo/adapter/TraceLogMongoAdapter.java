package com.pragma.powerup.infrastructure.out.mongo.adapter;

import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.domain.spi.ITraceLogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongo.document.TraceLogDocument;
import com.pragma.powerup.infrastructure.out.mongo.mapper.ITraceDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.ITraceLogMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TraceLogMongoAdapter implements ITraceLogPersistencePort {

    private final ITraceLogMongoRepository repository;
    private final ITraceDocumentMapper mapper;

    @Override
    public TraceLog save(TraceLog log) {
        TraceLogDocument doc = mapper.toDocument(log);
        TraceLogDocument saved = repository.save(doc);
        return mapper.toModel(saved);
    }

    @Override
    public List<TraceLog> findByOrderIdAndClientIdOrderByChangedAtAsc(Long orderId, Long clientId) {
        return repository.findByOrderIdAndClientIdOrderByChangedAtAsc(orderId, clientId)
                .stream()
                .map(mapper::toModel)
                .toList();
    }

}