package com.pragma.powerup.domain.validations;

import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.domain.model.OrderStatus;
import com.pragma.powerup.domain.model.TraceLog;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class OrderEfficiencyCalculator {

    public Optional<OrderEfficiencyDto> calculate(Long orderId, List<TraceLog> logs) {

        Optional<TraceLog> start = logs.stream()
                .filter(l -> OrderStatus.IN_PREPARATION.name().equals(l.getNewStatus()))
                .findFirst();

        Optional<TraceLog> end = logs.stream()
                .filter(l -> OrderStatus.DELIVERED.name().equals(l.getNewStatus()))
                .findFirst();

        if (start.isEmpty() || end.isEmpty()) {
            return Optional.empty();
        }

        long seconds = Duration.between(
                start.get().getChangedAt(),
                end.get().getChangedAt()
        ).getSeconds();

        return Optional.of(
                new OrderEfficiencyDto(
                        orderId,
                        end.get().getChangedByUserId(),
                        seconds
                )
        );
    }
}
