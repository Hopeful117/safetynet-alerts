package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;

import java.util.List;

public interface FloodResponseService {
    FloodResponseDTO getFloodResponseByStationNumbers(List<Integer> stationNumbers);
}
