package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller pour gérer les requêtes liées aux personnes.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PersonController {
    private final PersonService personService;


    /**
     * Gère les requêtes POST pour ajouter une nouvelle personne.
     * @param person
     * @return
     */
    @PostMapping("/person")
    public ResponseEntity<PersonRequestDTO> addPerson(@RequestBody PersonRequestDTO person) {
        try {

            log.info("Requête POST /person reçue");
            boolean added = personService.addPerson(person);
            if (added) {
                log.info("Personne ajoutée: {} {}", person.getFirstName(), person.getLastName());
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

            log.error("La personne existe deja: {} {}", person.getFirstName(), person.getLastName());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        catch (Exception e) {
            log.error("Erreur lors de l'ajout de la personne: {} {}", person.getFirstName(), person.getLastName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
    /**
     * Gère les requêtes PUT pour mettre à jour une personne existante.
     * @param person
     * @return
     */
    @PutMapping("/person")
    public ResponseEntity<PersonRequestDTO> updatePerson(@RequestBody PersonRequestDTO person) {
        try {
            log.info("Requête PUT /person reçue pour {} {}", person.getFirstName(), person.getLastName());
            boolean updated = personService.updatePerson(person);
            if (updated) {
                log.info("Personne mise à jour: {} {}", person.getFirstName(), person.getLastName());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(person);
            }

            log.error("Personne introuvable : {} {}", person.getFirstName(), person.getLastName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la personne: {} {}", person.getFirstName(), person.getLastName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

      }

/**
     * Gère les requêtes DELETE pour supprimer une personne existante.
     * @param firstName
     * @param lastName
     * @return
     */
    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            log.info("Requête DELETE /person reçue pour {} {}", firstName, lastName);
            boolean deleted = personService.deletePerson(firstName, lastName);
            if (deleted) {
                return ResponseEntity.ok().build();
            }

            log.error("Erreur lors de la suppression de la personne: {} {}", firstName, lastName);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e) {
            log.error("Erreur lors de la suppression de la personne: {} {}", firstName, lastName, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
