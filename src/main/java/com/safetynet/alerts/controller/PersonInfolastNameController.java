package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.PersonInfolastNameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller pour gérer les requêtes liées aux informations des personnes par nom de famille.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PersonInfolastNameController {

    private final PersonInfolastNameService personInfolastNameService;

    /**
     * Gère les requêtes GET pour obtenir les informations des personnes basées sur le nom de famille.
     *
     * @param lastName Le nom de famille des personnes recherchées.
     * @return Un objet PersonInfolastNameDTO contenant les informations des personnes.
     */
    @GetMapping("/personInfolastName/lastName")
    public ResidentsDTO getPersonInfolastName(@RequestParam String lastName) {
        log.info("Requête GET /personInfolastName/lastName reçue");
        ResidentsDTO response = personInfolastNameService.getPersonInfoByLastName(lastName);
        log.info("Réponse GET /personInfolastName/lastName traitée");
        return response;

    }

}
