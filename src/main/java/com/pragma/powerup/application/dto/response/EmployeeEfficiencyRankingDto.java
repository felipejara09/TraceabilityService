package com.pragma.powerup.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeEfficiencyRankingDto {
    private Long employeeId;
    private long averageDurationSeconds;
}

