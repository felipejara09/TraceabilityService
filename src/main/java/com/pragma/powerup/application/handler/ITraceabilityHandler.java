package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.RegisterTraceLogRequestDto;
import com.pragma.powerup.application.dto.response.TraceLogResponseDto;

import java.util.List;

public interface ITraceabilityHandler {
    void registerLog(RegisterTraceLogRequestDto dto);
    List<TraceLogResponseDto> getClientOrderTraceability(Long orderId, Long clientId);
}