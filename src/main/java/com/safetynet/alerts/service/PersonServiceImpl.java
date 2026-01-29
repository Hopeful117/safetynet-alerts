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
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");


    /**
     * Adds a new person to the repository.
     *
     * @param personRequestDTO
     * @return Person
     */
    @Override
    public Person addPerson(PersonRequestDTO personRequestDTO) {
        log.info("Ajout d'une nouvelle personne : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
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
        log.debug("Personne ajoutée avec succès : {} {}", person.getFirstName(), person.getLastName());

        return person;
    }
    /**
     * Updates an existing person's information.
     * @param personRequestDTO
     * @return
     */
    @Override
    public Person updatePerson(PersonRequestDTO personRequestDTO) {
        log.info("Mise à jour de la personne : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
        Optional<Person> existingPerson = personRepository.getAll().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(personRequestDTO.getFirstName()))
                .filter(p->p.getLastName().equalsIgnoreCase(personRequestDTO.getLastName()))
                        .findFirst();



        if (existingPerson.isEmpty()){
            log.error("Erreur lors de la recherche");
            return null;
        }
            existingPerson.ifPresent(p->{
                p.setAddress(personRequestDTO.getAddress());
                p.setCity(personRequestDTO.getCity());
                p.setZip(personRequestDTO.getZip());
                p.setPhone(personRequestDTO.getPhone());
                p.setEmail(personRequestDTO.getEmail());
                log.info("Personne mise à jour avec succès : {} {}", p.getFirstName(), p.getLastName());
                personRepository.save(p);
                return p;

            });






    }
    }

            )




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
        personRepository.delete(personToDelete);
        log.debug("Personne supprimée avec succès : {} {}", firstName, lastName);
        return true;


}
    }

