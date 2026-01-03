package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.TraceLog;
import java.util.List;

public interface ITraceLogPersistencePort {
    TraceLog save(TraceLog log);
    List<TraceLog> findByOrderIdAndClientIdOrderByChangedAtAsc(Long orderId, Long clientId);
}