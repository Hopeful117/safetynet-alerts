package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {
    @Mock
    SafetyNetRepository safetyNetRepository;
    PersonRepositoryImpl personRepository;
    @BeforeEach
    void setUp() {
        personRepository = new PersonRepositoryImpl(safetyNetRepository);
        when (safetyNetRepository.getPersons()).thenReturn(new ArrayList<>(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john.boyd@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "jacob.boyd@email.com"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@email.com")
        )));
    }
    @Test
     void getAll_ShouldReturnAllPersons() {
        List<Person> persons = personRepository.getAll();
        assert persons.size() == 3;
     }
     @Test
     void geAllByAddress_ShouldReturnPersons() {
        List<Person> persons = personRepository.getAllByAddress("1509 Culver St");
        assert persons.size() == 3;
     }
     @Test
      void findByFirstNameAndLastName_ShouldReturnPerson() {
        Person person = personRepository.findByFirstnameAndLastname("John", "Boyd").orElse(null);
        assert person != null;
        assert person.getFirstName().equals("John");
        assert person.getLastName().equals("Boyd");
     }
     @Test
      void findByFirstNameAndLastName_ShouldReturnEmpty() {
        assert personRepository.findByFirstnameAndLastname("Jane", "Doe").isEmpty();
     }

     @Test
      void save_ShouldAddPerson() {
          Person newPerson = new Person("Jane", "Doe", "123 Main St", "Anytown", "12345", "555-555-5555", "");
          personRepository.save(newPerson);
          assert personRepository.getAll().size() == 4;
      }

      @Test
        void delete_ShouldRemovePerson() {
            Person person = personRepository.findByFirstnameAndLastname("John", "Boyd").orElse(null);
            assert person != null;
            personRepository.delete(person);
            assert personRepository.getAll().size() == 2;
        }
}
