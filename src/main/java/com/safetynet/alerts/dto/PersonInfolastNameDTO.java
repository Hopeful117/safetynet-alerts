package com.safetynet.alerts.dto;

import java.util.List;
/**
 * DTO representing person information filtered by last name,
 * containing a list of residents with that last name.
 */
public class PersonInfolastNameDTO {
    private List<ResidentsDTO> residents;
    public PersonInfolastNameDTO(List<ResidentsDTO> residents) {
        this.residents = residents;
    }
    public List<ResidentsDTO> getResidents() {
        return residents;
}
}
