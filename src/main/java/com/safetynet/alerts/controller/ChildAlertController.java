package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * Controller pour gérer les requêtes liées aux alertes pour enfants.
 */
@RestController
public class ChildAlertController {
    private final static Logger LOGGER= LogManager.getLogger(ChildAlertController.class);
    private final ChildAlertService service;

    public ChildAlertController(ChildAlertService service) {
        this.service = service;
    }
/**
     * Gère les requêtes GET pour obtenir les informations des enfants vivant à une adresse donnée.
     *
     * @param address L'adresse pour laquelle obtenir les informations des enfants.
     * @return Un objet ChildAlertResponseDTO contenant les informations des enfants et des adultes associés.
     */
    @GetMapping("/childAlert")
    public ChildAlertResponseDTO getChildAlert(@RequestParam String address) {
        LOGGER.info("Requête GET /childAlert?address={} reçue", address);
        ChildAlertResponseDTO response = service.getChildAlertByAddress(address);
        LOGGER.info("Réponse GET /childAlert pour l'adresse {}: {} enfants trouvés",
                address, response.getChildren().size());
        return response;
    }
}
