package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import com.safetynet.alerts.service.CommunityEmailResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour gérer les requêtes liées aux emails communautaires.

 */
@RestController
public class CommunityEmailResponseController {
    private final static Logger LOGGER= LogManager.getLogger(CommunityEmailResponseController.class);
    private final CommunityEmailResponseService service;
    public CommunityEmailResponseController(CommunityEmailResponseService service) {
        this.service = service;
    }
    /**
     * Gère la requête GET pour obtenir les emails des habitants d'une ville spécifique.
     *
     * @param city La ville pour laquelle les emails sont demandés.
     * @return Un objet CommunityEmailResponseDTO contenant la liste des emails.
     */
    @GetMapping("/communityEmail")
    public CommunityEmailResponseDTO getCommunityEmailResponse(@RequestParam String city) {
        LOGGER.info("Requête GET /communityEmail?city={} reçue", city);
        CommunityEmailResponseDTO response = service.getCommunityEmailResponse(city);
        LOGGER.info("Réponse GET /communityEmail pour la ville {}: {} emails trouvés",
                city, response.getEmails().size());
        return response;
    }
}
