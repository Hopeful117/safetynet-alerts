package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.service.FireResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Contrôleur pour gérer les requêtes liées aux interventions incendie.
 */
@RestController
public class FireController {
    private static final Logger LOGGER = LogManager.getLogger(FireController.class);
    private final FireResponseService fireResponseService;
    public FireController(FireResponseService fireResponseService) {
        this.fireResponseService = fireResponseService;
    }

   /**
     * Gère les requêtes GET pour obtenir les informations d'intervention incendie basées sur une adresse.
     *
     * @param address L'adresse pour laquelle obtenir les informations d'intervention incendie.
     * @return Un objet FireResponseDTO contenant les informations d'intervention incendie.
     */
    @GetMapping("/fire")
    public FireResponseDTO getFireResponse(@RequestParam String address) {
        LOGGER.info("Requête GET /fire?address={} reçue", address);
       FireResponseDTO response= fireResponseService.getFireResponseByAddress(address);
        LOGGER.info("Réponse GET /fire traitée pour l'adresse {}", address);
        return response;

    }

}
