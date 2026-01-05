package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PhoneAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller pour gérer les requêtes liées aux alertes téléphoniques.
 */
@RestController
public class PhoneAlertController {
    private final PhoneAlertService phoneAlertService;
    private static final Logger LOGGER = LogManager.getLogger(PhoneAlertController.class);
    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService= phoneAlertService;
    }
    /**
     * Gère les requêtes GET pour obtenir les numéros de téléphone des personnes couvertes par une station de pompiers spécifique.
     *
     * @param firestation Le numéro de la station de pompiers.
     * @return Un objet PhoneAlertResponseDTO contenant les numéros de téléphone.
     */
    @GetMapping("/phoneAlert")
    public PhoneAlertResponseDTO getPhoneAlert(@RequestParam int firestation) {
        LOGGER.info("Requête GET /phoneAlert?firestation={} reçue", firestation);
       PhoneAlertResponseDTO response = phoneAlertService.getPhoneAlertByStationNumber(firestation);
        LOGGER.info("Réponse GET /phoneAlert: {} numéros de téléphone trouvés", response.getPhones().size());
        return response;
    }


}
