package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.RegisterTraceLogRequestDto;
import com.pragma.powerup.application.dto.response.TraceLogResponseDto;
import com.pragma.powerup.application.handler.ITraceabilityHandler;
import com.pragma.powerup.application.mapper.ITraceLogRequestMapper;
import com.pragma.powerup.application.mapper.ITraceLogResponseMapper;
import com.pragma.powerup.domain.api.ITraceabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityService traceabilityService;
    private final ITraceLogRequestMapper requestMapper;
    private final ITraceLogResponseMapper responseMapper;

    @Override
    public void registerLog(RegisterTraceLogRequestDto dto) {
        traceabilityService.registerLog(requestMapper.toModel(dto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TraceLogResponseDto> getClientOrderTraceability(Long orderId, Long clientId) {
        return traceabilityService.getClientOrderTraceability(orderId, clientId)
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }
}