package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ITraceabilityService;
import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.domain.spi.ITraceLogPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityService {

    private final ITraceLogPersistencePort persistencePort;

    @Override
    public void registerLog(TraceLog log) {
        if (log.getChangedAt() == null) {
            log.setChangedAt(LocalDateTime.now());
        }
        persistencePort.save(log);
    }

    @Override
    public List<TraceLog> getClientOrderTraceability(Long orderId, Long clientId) {
        return persistencePort.findByOrderIdAndClientIdOrderByChangedAtAsc(orderId, clientId);
    }
}