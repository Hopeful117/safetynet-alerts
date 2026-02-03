package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import com.safetynet.alerts.service.CommunityEmailResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur pour gérer les requêtes liées aux emails communautaires.

 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class CommunityEmailResponseController {

    private final CommunityEmailResponseService service;

    /**
     * Gère la requête GET pour obtenir les emails des habitants d'une ville spécifique.
     *
     * @param city La ville pour laquelle les emails sont demandés.
     * @return Un objet CommunityEmailResponseDTO contenant la liste des emails.
     */
    @GetMapping("/communityEmail")
    public CommunityEmailResponseDTO getCommunityEmailResponse(@RequestParam String city) {
        log.info("Requête GET /communityEmail?city={} reçue", city);
        CommunityEmailResponseDTO response = service.getCommunityEmailResponse(city);
        log.info("Réponse GET /communityEmail pour la ville {}: {} emails trouvés",
                city, response.getEmails().size());
        return response;
    }
}
