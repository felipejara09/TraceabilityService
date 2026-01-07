package com.pragma.powerup.domain.validations;

import com.pragma.powerup.domain.model.TraceLog;

import java.util.List;

public class TraceabilityEfficiencyValidator {

    public void validateLogsExist(List<TraceLog> logs) {
        if (logs == null || logs.isEmpty()) {
            throw new IllegalStateException("No trace logs found for restaurant");
        }
    }
}
