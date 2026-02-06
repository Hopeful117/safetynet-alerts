package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    final SafetyNetRepository safetyNetRepository;


    @Override
    public List<Person> getAll() {
        return safetyNetRepository.getPersons();
    }

    @Override
    public List<Person> getAllByAddress(String address) {
        return getAll().stream()
                .filter(p -> p.getAddress().trim().equalsIgnoreCase(address.trim()))
                .toList();
    }

    @Override
    public Optional<Person> findByFirstnameAndLastname(String firstName, String lastName) {
        return getAll()
                .stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public void save(Person person) {
        getAll().add(person);
    }

    @Override
    public void delete(Person person){
        getAll().remove(person);
    }
}
