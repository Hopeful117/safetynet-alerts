package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;


/**
 * Service interface for retrieving person information by last name.
 */

public interface PersonInfolastNameService {
    public PersonInfolastNameDTO getPersonInfoByLastName(String lastName);
}
