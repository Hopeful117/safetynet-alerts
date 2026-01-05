package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.FloodResponseService;
import com.safetynet.alerts.service.PersonInfolastNameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Controller pour gérer les requêtes liées aux informations des personnes par nom de famille.
 */
@RestController
public class PersonInfolastNameController {
    private static final Logger LOGGER = LogManager.getLogger(PersonInfolastNameController.class);
    private final PersonInfolastNameService personInfolastNameService;
    public PersonInfolastNameController(PersonInfolastNameService personInfolastNameService) {
        this.personInfolastNameService = personInfolastNameService;
    }
    /**
     * Gère les requêtes GET pour obtenir les informations des personnes basées sur le nom de famille.
     *
     * @param lastName Le nom de famille des personnes recherchées.
     * @return Un objet PersonInfolastNameDTO contenant les informations des personnes.
     */
    @GetMapping("/personInfolastName/lastName")
    public PersonInfolastNameDTO getPersonInfolastName(@RequestParam String lastName) {
        LOGGER.info("Requête GET /personInfolastName/lastName reçue");
        PersonInfolastNameDTO response= personInfolastNameService.getPersonInfoByLastName(lastName);
        LOGGER.info("Réponse GET /personInfolastName/lastName traitée");
        return response;

    }

}
