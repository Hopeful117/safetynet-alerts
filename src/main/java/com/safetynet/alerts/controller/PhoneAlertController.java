package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Controller pour gérer les requêtes liées aux alertes téléphoniques.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class PhoneAlertController {
    private final PhoneAlertService phoneAlertService;

    /**
     * Gère les requêtes GET pour obtenir les numéros de téléphone des personnes couvertes par une station de pompiers spécifique.
     *
     * @param firestation Le numéro de la station de pompiers.
     * @return Un objet PhoneAlertResponseDTO contenant les numéros de téléphone.
     */
    @GetMapping("/phoneAlert")
    public Set<String> getPhoneAlert(@RequestParam int firestation) {
        log.debug("Requête GET /phoneAlert?firestation={} reçue", firestation);
        Set<String> response = phoneAlertService.getPhoneAlertByStationNumber(firestation);
        log.info("Réponse GET /phoneAlert: {} numéros de téléphone trouvés", response.size());
        return response;
    }


}
