package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.application.dto.response.EmployeeEfficiencyRankingDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;
import com.pragma.powerup.domain.api.ITraceabilityEfficiencyService;
import com.pragma.powerup.domain.model.TraceLog;
import com.pragma.powerup.domain.validations.OrderEfficiencyCalculator;
import com.pragma.powerup.domain.validations.TraceabilityEfficiencyValidator;
import com.pragma.powerup.infrastructure.out.mongo.repository.ITraceLogMongoRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TraceabilityEfficiencyUseCase implements ITraceabilityEfficiencyService {

    private final ITraceLogMongoRepository traceLogRepository;
    private final TraceabilityEfficiencyValidator validator;
    private final OrderEfficiencyCalculator calculator;

    @Override
    public List<OrderEfficiencyDto> getOrderEfficiency(Long restaurantId) {

        List<TraceLog> logs = traceLogRepository.findByRestaurantId(restaurantId);
        validator.validateLogsExist(logs);

        return logs.stream()
                .collect(Collectors.groupingBy(TraceLog::getOrderId))
                .entrySet()
                .stream()
                .map(entry ->
                        calculator.calculate(entry.getKey(), entry.getValue())
                )
                .flatMap(Optional::stream)
                .toList();
    }

    @Override
    public List<EmployeeEfficiencyRankingDto> getEmployeeEfficiencyRanking(Long restaurantId) {

        Map<Long, Double> avgByEmployee =
                getOrderEfficiency(restaurantId).stream()
                        .collect(Collectors.groupingBy(
                                OrderEfficiencyDto::getEmployeeId,
                                Collectors.averagingLong(OrderEfficiencyDto::getDurationSeconds)
                        ));

        return avgByEmployee.entrySet().stream()
                .map(e -> new EmployeeEfficiencyRankingDto(
                        e.getKey(),
                        e.getValue().longValue()
                ))
                .sorted(Comparator.comparingLong(
                        EmployeeEfficiencyRankingDto::getAverageDurationSeconds
                ))
                .toList();
    }
}
