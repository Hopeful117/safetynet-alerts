package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.service.FireResponseService;
import com.safetynet.alerts.service.FloodResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Controller pour gérer les requêtes liées aux inondations.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class FloodController {
  ;
    private final FloodResponseService floodResponseService;

    /**
     * Gère les requêtes GET pour obtenir les informations d'inondation basées sur les numéros de station.
     *
     * @param station Liste des numéros de station.
     * @return Un objet FloodResponseDTO contenant les informations d'inondation.
     */
    @GetMapping("/flood/stations")
    public FloodResponseDTO getFloodResponse(@RequestParam int station) {
        log.info("Requête GET /flood/stations reçue");
        FloodResponseDTO response= floodResponseService.getFloodResponseByStationNumbers(station);
        log.info("Réponse GET /flood/stations traitée");
        return response;

    }
}
