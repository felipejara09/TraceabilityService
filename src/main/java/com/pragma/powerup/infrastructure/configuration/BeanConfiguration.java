package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ITraceabilityService;
import com.pragma.powerup.domain.spi.ITraceLogPersistencePort;
import com.pragma.powerup.domain.usecase.TraceabilityUseCase;
import com.pragma.powerup.infrastructure.out.mongo.adapter.TraceLogMongoAdapter;
import com.pragma.powerup.infrastructure.out.mongo.mapper.ITraceDocumentMapper;
import com.pragma.powerup.infrastructure.out.mongo.repository.ITraceLogMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public ITraceabilityService traceabilityService(ITraceLogPersistencePort persistencePort) {
        return new TraceabilityUseCase(persistencePort);
    }
}