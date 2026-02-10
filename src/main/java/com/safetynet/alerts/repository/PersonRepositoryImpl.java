package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * The PersonRepositoryImpl class is responsible for managing Person entities. It provides methods to retrieve all persons, find persons by address, and find a person by their first and last name. It also allows saving and deleting Person entities. This class uses the SafetyNetRepository to access the underlying data storage.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    final SafetyNetRepository safetyNetRepository;


    /**
     * Retrieves all Person entities from the SafetyNetRepository.
     * @return a list of all Person entities
     */
    @Override
    public List<Person> getAll() {
        return safetyNetRepository.getPersons();
    }


    /**
     * Retrieves a list of Person entities that match the specified address.
     * @param address the address to filter by
     * @return a list of Person entities with the specified address
     */
    @Override
    public List<Person> getAllByAddress(String address) {
        return getAll().stream()
                .filter(p -> p.getAddress().trim().equalsIgnoreCase(address.trim()))
                .toList();
    }

    /**
    * Retrieves a Person entity that matches the specified first and last name.
    * @param firstName the first name of the person to find
    * @param lastName the last name of the person to find
    * @return an Optional containing the found Person entity, or empty if not found
    */
    @Override
    public Optional<Person> findByFirstnameAndLastname(String firstName, String lastName) {
        return getAll()
                .stream()
                .filter(p -> p.getFirstName().trim().equalsIgnoreCase(firstName))
                .filter(p -> p.getLastName().trim().equalsIgnoreCase(lastName))
                .findFirst();
    }


    /**
     * Saves a Person entity to the repository. If the person already exists, it will be updated.
     * @param person the Person entity to save
     */
    @Override
    public void save(Person person) {
        getAll().add(person);
    }

    /**
     * Deletes a Person entity from the repository.
     * @param person the Person entity to delete
     */
    @Override
    public void delete(Person person){
        getAll().remove(person);
    }
}
