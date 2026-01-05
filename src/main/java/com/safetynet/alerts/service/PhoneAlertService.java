package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
/**
 * Service interface for retrieving phone alerts based on fire station number.
 */
public interface PhoneAlertService {
    PhoneAlertResponseDTO getPhoneAlertByStationNumber(int stationNumber);
}
