package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.domain.validations.OrderEfficiencyCalculator;
import com.pragma.powerup.domain.validations.TraceabilityEfficiencyValidator;
import com.pragma.powerup.infrastructure.out.mongo.repository.ITraceLogMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TraceabilityEfficiencyUseCaseTest {

    private ITraceLogMongoRepository repository;
    private TraceabilityEfficiencyUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ITraceLogMongoRepository.class);
        useCase = new TraceabilityEfficiencyUseCase(
                repository,
                new TraceabilityEfficiencyValidator(),
                new OrderEfficiencyCalculator()
        );
    }

    @Test
    void shouldCalculateOrderEfficiencySuccessfully() {

        TraceLog start = new TraceLog();
        start.setOrderId(1L);
        start.setNewStatus("IN_PREPARATION");
        start.setChangedAt(LocalDateTime.now().minusMinutes(10));

        TraceLog end = new TraceLog();
        end.setOrderId(1L);
        end.setNewStatus("DELIVERED");
        end.setChangedAt(LocalDateTime.now());
        end.setChangedByUserId(9L);

        when(repository.findByRestaurantId(1L))
                .thenReturn(List.of(start, end));

        List<OrderEfficiencyDto> result =
                useCase.getOrderEfficiency(1L);

        assertEquals(1, result.size());
        assertEquals(9L, result.get(0).getEmployeeId());
        assertTrue(result.get(0).getDurationSeconds() > 0);
    }
}

