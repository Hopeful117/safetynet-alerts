package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for Firestation requests.
 *
 */
@Data
@AllArgsConstructor
public class FirestationRequestDTO {
    private String address;
    private int station;


}
