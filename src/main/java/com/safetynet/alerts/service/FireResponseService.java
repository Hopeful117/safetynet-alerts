package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;

public interface FireResponseService
{
    FireResponseDTO getFireResponseByAddress(String address);
}
