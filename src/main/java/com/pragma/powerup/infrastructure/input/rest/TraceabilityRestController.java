package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.RegisterTraceLogRequestDto;
import com.pragma.powerup.application.dto.response.EmployeeEfficiencyRankingDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.application.dto.response.TraceLogResponseDto;
import com.pragma.powerup.domain.api.ITraceabilityEfficiencyService;
import com.pragma.powerup.domain.api.ITraceabilityService;
import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.infrastructure.configuration.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final ITraceabilityEfficiencyService efficiencyService;

    @Operation(
            summary = "Register order trace log",
            description = "Registers a new trace log entry for an order status change. " +
                    "This endpoint is intended for internal service-to-service communication."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Trace log registered successfully",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid trace log data",
                    content = @Content
            )
    })

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

    @Operation(
            summary = "Get order traceability",
            description = "Allows a client to retrieve the status change history of their own order."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order traceability retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TraceLogResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only clients can access this resource",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Order not found or does not belong to the client",
                    content = @Content
            )
    })

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

    @Operation(
            summary = "Get order efficiency",
            description = "Allows a restaurant owner to retrieve the preparation efficiency of each completed order " +
                    "by calculating the time between order start and order delivery."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Order efficiency retrieved successfully",
                    content = @Content(schema = @Schema(implementation = OrderEfficiencyDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only restaurant owners can access this resource",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurant not found",
                    content = @Content
            )
    })


    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("owner/restaurants/{restaurantId}/orders/efficiency")
    public ResponseEntity<List<OrderEfficiencyDto>> getOrderEfficiency(
            @PathVariable Long restaurantId
    ) {
        return ResponseEntity.ok(
                efficiencyService.getOrderEfficiency(restaurantId)
        );
    }

    @Operation(
            summary = "Get employee efficiency ranking",
            description = "Allows a restaurant owner to retrieve a ranking of employees based on the average " +
                    "time taken to prepare and deliver completed orders."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee efficiency ranking retrieved successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeEfficiencyRankingDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied. Only restaurant owners can access this resource",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurant not found",
                    content = @Content
            )
    })

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("owner/restaurants/{restaurantId}/employees/efficiency")
    public ResponseEntity<List<EmployeeEfficiencyRankingDto>> getEmployeeEfficiencyRanking(
            @PathVariable Long restaurantId
    ) {
        return ResponseEntity.ok(
                efficiencyService.getEmployeeEfficiencyRanking(restaurantId)
        );
    }
}