package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterTraceLogRequestDto;
import com.pragma.powerup.application.dto.response.TraceLogResponseDto;
import com.pragma.powerup.domain.api.ITraceabilityService;
import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.infrastructure.configuration.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TraceabilityRestController {

    private final ITraceabilityService traceabilityService;


    @PostMapping("/internal/trace-logs")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterTraceLogRequestDto dto) {

        TraceLog log = new TraceLog();
        log.setOrderId(dto.getOrderId());
        log.setClientId(dto.getClientId());
        log.setRestaurantId(dto.getRestaurantId());
        log.setPreviousStatus(dto.getPreviousStatus());
        log.setNewStatus(dto.getNewStatus());
        log.setChangedByUserId(dto.getChangedByUserId());
        log.setChangedByRole(dto.getChangedByRole());

        traceabilityService.registerLog(log);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/client/orders/{orderId}/traceability")
    public ResponseEntity<List<TraceLogResponseDto>> getMyOrderTraceability(@PathVariable Long orderId) {

        Long clientId = SecurityUtils.getUserId();

        List<TraceLogResponseDto> response =
                traceabilityService.getClientOrderTraceability(orderId, clientId)
                        .stream()
                        .map(l -> new TraceLogResponseDto(
                                l.getPreviousStatus(),
                                l.getNewStatus(),
                                l.getChangedAt(),
                                l.getChangedByUserId(),
                                l.getChangedByRole()
                        ))
                        .toList();

        return ResponseEntity.ok(response);
    }
}