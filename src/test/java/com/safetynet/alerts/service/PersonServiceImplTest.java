package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.PersonRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for PersonServiceImpl.
 */
class PersonServiceImplTest {

    private PersonRepository personRepository;
    private PersonServiceImpl service;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepositoryImpl.class);
        service = new PersonServiceImpl(personRepository);
    }
    /**
     * Test for addPerson method.
     */
    @Test
    void addPerson_shouldAddPersonToRepository() {
        // GIVEN
        List<Person> persons = new ArrayList<>();
        when(personRepository.getAll()).thenReturn(persons);
        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                "123 Main St",
                "Culver",
                "97451",
                "111-111",
                "john@doe.com"
        );

        // WHEN
        boolean result = service.addPerson(dto);

        // THEN
        assertTrue(result);
    }
    /**
     * Test for updatePerson method.
     */
    @Test
    void updatePerson_shouldUpdateExistingPerson() {
        // GIVEN
        Person existing = new Person(
                "John", "Doe", "Old St", "OldCity", "00000", "000", "old@mail.com"
        );
        List<Person> persons = new ArrayList<>(List.of(existing));
        when(personRepository.getAll()).thenReturn(persons);

        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                "New St",
                "NewCity",
                "11111",
                "999",
                "new@mail.com"
        );

        // WHEN
        boolean updated = service.updatePerson(dto);

        // THEN
        assertTrue(updated);

    }
    /**
     * Test for updatePerson method when person does not exist.
     */
    @Test
    void updatePerson_shouldReturnFalseWhenPersonNotFound() {
        // GIVEN
        when(personRepository.getAll()).thenReturn(new ArrayList<>());

        PersonRequestDTO dto = new PersonRequestDTO(
                "Unknown",
                "Person",
                "Some St",
                "City",
                "00000",
                "000",
                "mail@mail.com"
        );

        // WHEN
        boolean updated = service.updatePerson(dto);

        // THEN
        assertFalse(updated);
    }
    /**
     * Test for deletePerson method.
     */
    @Test
    void deletePerson_shouldRemovePerson_whenExists() {
        // GIVEN
        Person person = new Person(
                "John", "Doe", "Street", "City", "00000", "000", "mail@mail.com"
        );
        List<Person> persons = new ArrayList<>(List.of(person));
        when(personRepository.findByFirstnameAndLastname(person.getFirstName(),person.getLastName())).thenReturn(Optional.of(person));

        // WHEN
        boolean deleted = service.deletePerson("John", "Doe");

        // THEN
        assertTrue(deleted);

    }
    /**
     * Test for deletePerson method when person does not exist.
     */
    @Test
    void deletePerson_shouldReturnFalse_whenPersonNotFound() {
        // GIVEN
        when(personRepository.getAll()).thenReturn(new ArrayList<>());

        // WHEN
        boolean deleted = service.deletePerson("Unknown", "Person");

        // THEN
        assertFalse(deleted);
    }
}
