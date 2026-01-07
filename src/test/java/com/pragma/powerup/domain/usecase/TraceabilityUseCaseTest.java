package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.domain.spi.ITraceLogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceabilityUseCaseTest {

    private ITraceLogPersistencePort persistencePort;
    private TraceabilityUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(ITraceLogPersistencePort.class);
        useCase = new TraceabilityUseCase(persistencePort);
    }


    @Test
    void shouldSetChangedAtWhenRegisteringLogIfNull() {

        TraceLog log = new TraceLog();
        log.setOrderId(1L);
        log.setClientId(7L);
        log.setNewStatus("PENDING");
        log.setChangedAt(null);

        useCase.registerLog(log);

        ArgumentCaptor<TraceLog> captor =
                ArgumentCaptor.forClass(TraceLog.class);

        verify(persistencePort).save(captor.capture());

        TraceLog savedLog = captor.getValue();

        assertNotNull(savedLog.getChangedAt(),
                "changedAt should be automatically set");
    }

    @Test
    void shouldKeepChangedAtIfAlreadyPresent() {

        LocalDateTime now = LocalDateTime.now();

        TraceLog log = new TraceLog();
        log.setOrderId(1L);
        log.setClientId(7L);
        log.setNewStatus("PENDING");
        log.setChangedAt(now);

        useCase.registerLog(log);

        ArgumentCaptor<TraceLog> captor =
                ArgumentCaptor.forClass(TraceLog.class);

        verify(persistencePort).save(captor.capture());

        assertEquals(
                now,
                captor.getValue().getChangedAt(),
                "changedAt should not be modified if already present"
        );
    }


    @Test
    void shouldReturnClientOrderTraceabilityOrderedByDate() {

        TraceLog log1 = new TraceLog();
        log1.setChangedAt(LocalDateTime.now().minusMinutes(5));

        TraceLog log2 = new TraceLog();
        log2.setChangedAt(LocalDateTime.now());

        when(
                persistencePort.findByOrderIdAndClientIdOrderByChangedAtAsc(
                        1L, 7L
                )
        ).thenReturn(List.of(log1, log2));

        List<TraceLog> result =
                useCase.getClientOrderTraceability(1L, 7L);

        assertEquals(2, result.size());
        assertEquals(log1, result.get(0));
        assertEquals(log2, result.get(1));

        verify(persistencePort)
                .findByOrderIdAndClientIdOrderByChangedAtAsc(1L, 7L);
    }
}
