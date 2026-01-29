package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> getAll();

    List<Person> getAllByAddress(String address);

    Optional<Person> findByFirstnameAndLastname(String firstName, String lastName);

    void save(Person person);

    void delete(Optional<Person> person);

}
