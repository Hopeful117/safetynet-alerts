package com.safetynet.alerts.dto;

import java.util.List;

public class PersonInfolastNameDTO {
    private List<ResidentsDTO> residents;
    public PersonInfolastNameDTO(List<ResidentsDTO> residents) {
        this.residents = residents;
    }
    public List<ResidentsDTO> getResidents() {
        return residents;
}
}
