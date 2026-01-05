package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
/**
 * Service interface for retrieving child alert information by address.
 */
public interface ChildAlertService {
    ChildAlertResponseDTO getChildAlertByAddress(String address);
}
