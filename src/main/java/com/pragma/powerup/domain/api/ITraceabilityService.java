package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.TraceLog;

import java.util.List;

public interface ITraceabilityService {

    void registerLog(TraceLog log);
    List<TraceLog> getClientOrderTraceability(Long orderId, Long clientId);
}