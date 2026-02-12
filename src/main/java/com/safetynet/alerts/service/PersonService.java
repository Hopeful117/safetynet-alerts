package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonRequestDTO;

/**
 * Service interface for managing person-related operations.
 */
public interface PersonService {
    public boolean addPerson(PersonRequestDTO personRequestDTO);

    public boolean updatePerson(PersonRequestDTO personRequestDTO);

    public boolean deletePerson(String firstName, String lastName);
}
