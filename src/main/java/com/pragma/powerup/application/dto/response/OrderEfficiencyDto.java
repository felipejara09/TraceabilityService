package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderEfficiencyDto {
    private Long orderId;
    private Long employeeId;
    private long durationSeconds;
}
