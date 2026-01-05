package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for PersonServiceImpl.
 */
class PersonServiceImplTest {

    private SafetyNetRepository repository;
    private PersonServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new PersonServiceImpl(repository);
    }
    /**
     * Test for addPerson method.
     */
    @Test
    void addPerson_shouldAddPersonToRepository() {
        // GIVEN
        List<Person> persons = new ArrayList<>();
        when(repository.getPersons()).thenReturn(persons);

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
        Person result = service.addPerson(dto);

        // THEN
        assertEquals(1, persons.size());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("123 Main St", result.getAddress());
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
        when(repository.getPersons()).thenReturn(persons);

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
        Person updated = service.updatePerson(dto);

        // THEN
        assertNotNull(updated);
        assertEquals("New St", updated.getAddress());
        assertEquals("NewCity", updated.getCity());
        assertEquals("999", updated.getPhone());
    }
    /**
     * Test for updatePerson method when person does not exist.
     */
    @Test
    void updatePerson_shouldReturnNull_whenPersonNotFound() {
        // GIVEN
        when(repository.getPersons()).thenReturn(new ArrayList<>());

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
        Person result = service.updatePerson(dto);

        // THEN
        assertNull(result);
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
        when(repository.getPersons()).thenReturn(persons);

        // WHEN
        boolean deleted = service.deletePerson("John", "Doe");

        // THEN
        assertTrue(deleted);
        assertTrue(persons.isEmpty());
    }
    /**
     * Test for deletePerson method when person does not exist.
     */
    @Test
    void deletePerson_shouldReturnFalse_whenPersonNotFound() {
        // GIVEN
        when(repository.getPersons()).thenReturn(new ArrayList<>());

        // WHEN
        boolean deleted = service.deletePerson("Unknown", "Person");

        // THEN
        assertFalse(deleted);
    }
}
