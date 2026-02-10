package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Person entities.
 * This interface defines methods for retrieving, saving, and deleting Person records.
 */

public interface PersonRepository {

    List<Person> getAll();

    List<Person> getAllByAddress(String address);

    Optional<Person> findByFirstnameAndLastname(String firstName, String lastName);

    void save(Person person);

    void delete(Person person);

}
