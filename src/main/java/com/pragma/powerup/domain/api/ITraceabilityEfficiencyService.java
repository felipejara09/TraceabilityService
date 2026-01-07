package com.pragma.powerup.domain.api;

import com.pragma.powerup.application.dto.response.EmployeeEfficiencyRankingDto;
import com.pragma.powerup.application.dto.response.OrderEfficiencyDto;

import java.util.List;

public interface ITraceabilityEfficiencyService {

    List<OrderEfficiencyDto> getOrderEfficiency(Long restaurantId);

    List<EmployeeEfficiencyRankingDto> getEmployeeEfficiencyRanking(Long restaurantId);
}
