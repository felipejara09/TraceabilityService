package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraceLog {
    private String id;
    private Long orderId;
    private Long clientId;
    private Long restaurantId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;
    private Long changedByUserId;
    private String changedByRole;
}
