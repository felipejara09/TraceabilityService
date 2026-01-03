package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterTraceLogRequestDto {

    @NotNull
    private Long orderId;

    @NotNull
    private Long clientId;

    @NotNull
    private Long restaurantId;

    @NotBlank
    private String previousStatus;

    @NotBlank
    private String newStatus;

    @NotNull
    private Long changedByUserId;

    @NotBlank
    private String changedByRole;
}
