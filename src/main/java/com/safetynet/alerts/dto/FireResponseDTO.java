package com.safetynet.alerts.dto;


import java.util.List;

/**
 * DTO representing the response for a fire incident,
 * including residents affected and the fire station number.
 */
public class FireResponseDTO {
    private List<ResidentsDTO> residents;
    private int stationNumber;
    public FireResponseDTO(List<ResidentsDTO> residents, int stationNumber) {
        this.residents = residents;
        this.stationNumber = stationNumber;
    }
    public List<ResidentsDTO> getResidents() {
        return residents;
    }
    public int getStationNumber() {
        return stationNumber;
    }

}
