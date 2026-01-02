package com.safetynet.alerts.dto;

import java.util.List;
import java.util.Map;
public class FloodResponseDTO {

    private Map<String , List<ResidentsDTO>> households;
    public FloodResponseDTO(Map<String, List<ResidentsDTO>> households)  {
        this.households = households;
    }
    public Map<String, List<ResidentsDTO>> getHouseholds() {
        return households;
    }
}
