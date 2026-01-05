package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
/**
 * Service interface for managing person-related operations.
 */
public interface PersonService {
    public Person addPerson(PersonRequestDTO personRequestDTO);
    public Person updatePerson(PersonRequestDTO personRequestDTO);
    public boolean deletePerson(String firstName, String lastName);
}
