package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
/**
 * Service implementation for managing persons.
 */
@Service
public class PersonServiceImpl implements PersonService{
    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final Logger LOGGER = LogManager.getLogger(PersonServiceImpl.class);
    public PersonServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }

    /**
     * Adds a new person to the repository.
     * @param personRequestDTO
     * @return
     */
    @Override
    public Person addPerson(PersonRequestDTO personRequestDTO) {
        LOGGER.info("Ajout d'une nouvelle personne : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
        Person person = new Person(
                personRequestDTO.getFirstName(),
                personRequestDTO.getLastName(),
                personRequestDTO.getAddress(),
                personRequestDTO.getCity(),
                personRequestDTO.getZip(),
                personRequestDTO.getPhone(),
                personRequestDTO.getEmail()
        );
        repository.getPersons().add(person);
        LOGGER.debug("Personne ajoutée avec succès : {} {}", person.getFirstName(), person.getLastName());
        return person;
    }
    /**
     * Updates an existing person's information.
     * @param personRequestDTO
     * @return
     */
    @Override
    public Person updatePerson(PersonRequestDTO personRequestDTO) {
        LOGGER.info("Mise à jour de la personne : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
        Person existingPerson = repository.getPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(personRequestDTO.getFirstName())
                        && p.getLastName().equalsIgnoreCase(personRequestDTO.getLastName()))
                .findFirst()
                .orElse(null);
        if (existingPerson != null) {
            existingPerson.setAddress(personRequestDTO.getAddress());
            existingPerson.setCity(personRequestDTO.getCity());
            existingPerson.setZip(personRequestDTO.getZip());
            existingPerson.setPhone(personRequestDTO.getPhone());
            existingPerson.setEmail(personRequestDTO.getEmail());
            LOGGER.debug("Personne mise à jour avec succès : {} {}", existingPerson.getFirstName(), existingPerson.getLastName());
            return existingPerson;
        } else {
            LOGGER.warn("Personne non trouvée pour la mise à jour : {} {}", personRequestDTO.getFirstName(), personRequestDTO.getLastName());
            return null;
        }
    }
    /**
     * Deletes a person from the repository.
     * @param firstName
     * @param lastName
     * @return
     */
    @Override
    public boolean deletePerson(String firstName, String lastName) {
        LOGGER.info("Suppression de la personne : {} {}", firstName, lastName);
        Person personToDelete = repository.getPersons().stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
        if (personToDelete != null) {
            repository.getPersons().remove(personToDelete);
            LOGGER.debug("Personne supprimée avec succès : {} {}", firstName, lastName);
            return true;
        } else {
            LOGGER.warn("Personne non trouvée pour la suppression : {} {}", firstName, lastName);
            return false;

}
    }
}
