package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ITraceabilityEfficiencyService;
import com.pragma.powerup.domain.api.ITraceabilityService;
import com.pragma.powerup.domain.spi.ITraceLogPersistencePort;
import com.pragma.powerup.domain.usecase.TraceabilityEfficiencyUseCase;
import com.pragma.powerup.domain.usecase.TraceabilityUseCase;
import com.pragma.powerup.domain.validations.OrderEfficiencyCalculator;
import com.pragma.powerup.domain.validations.TraceabilityEfficiencyValidator;
import com.pragma.powerup.infrastructure.out.mongo.adapter.TraceLogMongoAdapter;
import com.pragma.powerup.infrastructure.out.mongo.mapper.ITraceDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.ITraceLogMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ITraceLogMongoRepository traceLogRepository;

    @Bean
    public ITraceabilityService traceabilityService(ITraceLogPersistencePort persistencePort) {
        return new TraceabilityUseCase(persistencePort);
    }

    @Bean
    public TraceabilityEfficiencyValidator traceabilityEfficiencyValidator() {
        return new TraceabilityEfficiencyValidator();
    }

    @Bean
    public OrderEfficiencyCalculator orderEfficiencyCalculator() {
        return new OrderEfficiencyCalculator();
    }

    @Bean
    public TraceabilityEfficiencyUseCase traceabilityEfficiencyUseCase(
            ITraceLogMongoRepository traceLogRepository,
            TraceabilityEfficiencyValidator validator,
            OrderEfficiencyCalculator calculator
    ) {
        return new TraceabilityEfficiencyUseCase(
                traceLogRepository,
                validator,
                calculator
        );
    }
}
