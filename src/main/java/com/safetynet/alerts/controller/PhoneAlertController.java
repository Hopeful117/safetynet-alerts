package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.service.FirestationService;
import com.safetynet.alerts.service.PhoneAlertService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneAlertController {
    private final PhoneAlertService phoneAlertService;
    private static final Logger LOGGER = LogManager.getLogger(PhoneAlertController.class);
    public PhoneAlertController(PhoneAlertService phoneAlertService) {
        this.phoneAlertService= phoneAlertService;
    }
    @GetMapping("/phoneAlert")
    public PhoneAlertResponseDTO getPhoneAlert(@RequestParam int firestation) {
        LOGGER.info("Requête GET /phoneAlert?firestation={} reçue", firestation);
       PhoneAlertResponseDTO response = phoneAlertService.getPhoneAlertByStationNumber(firestation);
        LOGGER.info("Réponse GET /phoneAlert: {} numéros de téléphone trouvés", response.getPhones().size());
        return response;
    }


}
