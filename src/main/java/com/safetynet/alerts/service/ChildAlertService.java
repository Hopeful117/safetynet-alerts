package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;

public interface ChildAlertService {
    ChildAlertResponseDTO getChildAlertByAddress(String address);
}
