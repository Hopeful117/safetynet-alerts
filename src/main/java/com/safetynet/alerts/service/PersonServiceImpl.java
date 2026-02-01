package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Service implementation for managing persons.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{
    private final PersonRepository personRepository;


    /**
     * Adds a new person to the repository.
     *
     * @param personRequestDTO
     * @return Person
     */
    @Override
    public boolean addPerson(PersonRequestDTO personRequestDTO) {
        log.info("Ajout d'une nouvelle personne : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
        Optional<Person> exists = personRepository.findByFirstnameAndLastname(personRequestDTO.getFirstName(),personRequestDTO.getLastName());
        if (exists.isPresent()){
            log.warn("Personne déjà existante : {} {}",personRequestDTO.getFirstName(),personRequestDTO.getLastName());
            return false;
        }
        Person person = new Person(
                personRequestDTO.getFirstName(),
                personRequestDTO.getLastName(),
                personRequestDTO.getAddress(),
                personRequestDTO.getCity(),
                personRequestDTO.getZip(),
                personRequestDTO.getPhone(),
                personRequestDTO.getEmail()
        );
        personRepository.save(person);
        log.info("Personne ajoutée avec succès : {} {}", person.getFirstName(), person.getLastName());

    return false;
    }
    /**
     * Updates an existing person's information.
     * @param personRequestDTO
     * @return
     */
    @Override
    public boolean updatePerson(PersonRequestDTO personRequestDTO) {
        log.info("Mise à jour de la personne : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
        Optional<Person> existingPerson = personRepository.getAll().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(personRequestDTO.getFirstName()))
                .filter(p->p.getLastName().equalsIgnoreCase(personRequestDTO.getLastName()))
                        .findFirst();


            if (existingPerson.isPresent()){
                existingPerson.get().setAddress(personRequestDTO.getAddress());
                existingPerson.get().setCity(personRequestDTO.getCity());
                existingPerson.get().setZip(personRequestDTO.getZip());
                existingPerson.get().setPhone(personRequestDTO.getPhone());
                existingPerson.get().setEmail(personRequestDTO.getEmail());
                log.info("Personne mise à jour avec succès : {} {}", existingPerson.get().getFirstName(), existingPerson.get().getLastName());
                personRepository.save(existingPerson.get());
                return true;


            };
            return false;






    }




    /**
     * Deletes a person from the repository.
     * @param firstName
     * @param lastName
     * @return
     */
    @Override
    public boolean deletePerson(String firstName, String lastName) {

        log.info("Suppression de la personne : {} {}", firstName, lastName);
        Optional<Person> personToDelete = personRepository.findByFirstnameAndLastname(firstName,lastName);
        if (personToDelete.isPresent()){
            personRepository.delete(personToDelete.get());
            log.debug("Personne supprimée avec succès : {} {}", firstName, lastName);
            return true;

        }
        log.debug("Erreur lors de la suppression");
        return false;



}


}