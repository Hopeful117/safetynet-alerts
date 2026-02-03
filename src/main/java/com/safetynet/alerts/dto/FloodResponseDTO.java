package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * DTO representing the response for flood information,
 * containing households mapped by address with their residents' details.
 */
@Data
@AllArgsConstructor
public class FloodResponseDTO {

    private Map<String , List<ResidentsDTO>> households;

}
