package com.safetynet.alerts.dto;


import java.util.List;

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
