package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;
/**
 * Service interface for handling fire response operations.
 */
public interface FireResponseService
{
    FireResponseDTO getFireResponseByAddress(String address);
}
