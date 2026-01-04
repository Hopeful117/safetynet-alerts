package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import com.safetynet.alerts.service.CommunityEmailResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunityEmailResponseController {
    private final static Logger LOGGER= LogManager.getLogger(CommunityEmailResponseController.class);
    private final CommunityEmailResponseService service;
    public CommunityEmailResponseController(CommunityEmailResponseService service) {
        this.service = service;
    }
    @GetMapping("/communityEmail")
    public CommunityEmailResponseDTO getCommunityEmailResponse(@RequestParam String city) {
        LOGGER.info("Requête GET /communityEmail?city={} reçue", city);
        CommunityEmailResponseDTO response = service.getCommunityEmailResponse(city);
        LOGGER.info("Réponse GET /communityEmail pour la ville {}: {} emails trouvés",
                city, response.getEmails().size());
        return response;
    }
}
