package com.safetynet.alerts.service;

import java.util.Set;

/**
 * Service interface for retrieving phone alerts based on fire station number.
 */
public interface PhoneAlertService {
    Set<String> getPhoneAlertByStationNumber(int stationNumber);
}
