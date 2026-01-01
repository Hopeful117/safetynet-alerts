package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildAlertController {
    private final static Logger LOGGER= LogManager.getLogger(ChildAlertController.class);
    private final ChildAlertService service;

    public ChildAlertController(ChildAlertService service) {
        this.service = service;
    }

    @GetMapping("/childAlert")
    public ChildAlertResponseDTO getChildAlert(@RequestParam String address) {
        LOGGER.info("Requête GET /childAlert?address={} reçue", address);
        ChildAlertResponseDTO response = service.getChildAlertByAddress(address);
        LOGGER.info("Réponse GET /childAlert pour l'adresse {}: {} enfants trouvés",
                address, response.getChildren().size());
        return response;
    }
}
