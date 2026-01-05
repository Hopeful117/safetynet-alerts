package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.service.FireResponseService;
import com.safetynet.alerts.service.FloodResponseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Controller pour gérer les requêtes liées aux inondations.
 */
@RestController
public class FloodController {
    private static final Logger LOGGER = LogManager.getLogger(FloodController.class);
    private final FloodResponseService floodResponseService;
    public FloodController(FloodResponseService floodResponseService) {
        this.floodResponseService = floodResponseService;
    }
    /**
     * Gère les requêtes GET pour obtenir les informations d'inondation basées sur les numéros de station.
     *
     * @param stations Liste des numéros de station.
     * @return Un objet FloodResponseDTO contenant les informations d'inondation.
     */
    @GetMapping("/flood/stations")
    public FloodResponseDTO getFloodResponse(@RequestParam List<Integer> stations) {
        LOGGER.info("Requête GET /flood/stations reçue");
        FloodResponseDTO response= floodResponseService.getFloodResponseByStationNumbers(stations);
        LOGGER.info("Réponse GET /flood/stations traitée");
        return response;

    }
}
