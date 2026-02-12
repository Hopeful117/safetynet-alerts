package com.safetynet.alerts.service;


import com.safetynet.alerts.dto.ResidentsDTO;

/**
 * Service interface for retrieving person information by last name.
 */

public interface PersonInfolastNameService {
    public ResidentsDTO getPersonInfoByLastName(String lastName);
}
