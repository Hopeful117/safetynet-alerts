package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;

public interface PhoneAlertService {
    PhoneAlertResponseDTO getPhoneAlertByStationNumber(int stationNumber);
}
