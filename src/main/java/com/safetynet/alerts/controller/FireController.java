package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.service.FireResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour gérer les requêtes liées aux interventions incendie.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FireController {

    private final FireResponseService fireResponseService;


    /**
     * Gère les requêtes GET pour obtenir les informations d'intervention incendie basées sur une adresse.
     *
     * @param address L'adresse pour laquelle obtenir les informations d'intervention incendie.
     * @return Un objet FireResponseDTO contenant les informations d'intervention incendie.
     */
    @GetMapping("/fire")
    public FireResponseDTO getFireResponse(@RequestParam String address) {
        log.info("Requête GET /fire?address={} reçue", address);
        FireResponseDTO response = fireResponseService.getFireResponseByAddress(address);
        log.info("Réponse GET /fire traitée pour l'adresse {}", address);
        return response;

    }

}
