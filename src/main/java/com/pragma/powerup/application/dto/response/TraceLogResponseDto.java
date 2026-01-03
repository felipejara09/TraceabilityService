package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TraceLogResponseDto {
    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;
    private Long changedByUserId;
    private String changedByRole;
}
