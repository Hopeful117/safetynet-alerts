package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;

import java.util.Set;

/**
 * Service interface for retrieving flood response information
 * based on fire station numbers.
 */
public interface FloodResponseService {
    FloodResponseDTO getFloodResponseByStationNumbers(Set<Integer> stationNumbers);
}
