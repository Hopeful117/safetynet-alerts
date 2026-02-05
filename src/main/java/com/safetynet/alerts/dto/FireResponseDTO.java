package com.safetynet.alerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO representing the response for a fire incident,
 * including residents affected and the fire station number.
 */
@Data
@AllArgsConstructor
public class FireResponseDTO {
    private ResidentsDTO residents;
    private int stationNumber;


}
