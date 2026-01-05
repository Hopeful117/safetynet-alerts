package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;

import java.util.List;
/**
 * Service interface for retrieving flood response information
 * based on fire station numbers.
 */
public interface FloodResponseService {
    FloodResponseDTO getFloodResponseByStationNumbers(List<Integer> stationNumbers);
}
