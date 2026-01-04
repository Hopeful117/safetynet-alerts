package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {
    private final PersonService personService;
    private static final Logger LOGGER = LogManager.getLogger(PersonController.class);
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    @PostMapping("/person")
    public ResponseEntity<Person> addPerson(@RequestBody PersonRequestDTO person) {
      try {
          LOGGER.info("Requête POST /person reçue");
          personService.addPerson(person);
          LOGGER.info("Personne ajoutée: {} {}", person.getFirstName(), person.getLastName());
          return ResponseEntity.status(HttpStatus.CREATED).build();
      } catch (IllegalArgumentException e) {
          LOGGER.error("Erreur lors de l'ajout de la personne: {}", e.getMessage());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    }
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonRequestDTO person) {
      try{
            LOGGER.info("Requête PUT /person reçue pour {} {}", person.getFirstName(), person.getLastName());
            Person updatedPerson = personService.updatePerson(person);
            LOGGER.info("Personne mise à jour: {} {}", person.getFirstName(), person.getLastName());
            return ResponseEntity.ok(updatedPerson);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erreur lors de la mise à jour de la personne: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    }

    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
      try {
          LOGGER.info("Requête DELETE /person reçue pour {} {}", firstName, lastName);
          boolean deleted = personService.deletePerson(firstName, lastName);
          if (!deleted) {
              return ResponseEntity.notFound().build();
          }
          return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      } catch (IllegalArgumentException e) {
          LOGGER.error("Erreur lors de la suppression de la personne: {}", e.getMessage());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    }

}
