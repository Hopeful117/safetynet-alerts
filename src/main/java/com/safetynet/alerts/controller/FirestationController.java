package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.dto.FirestationRequestDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {
    private final FirestationService firestationService;
    private static final Logger LOGGER = LogManager.getLogger(FirestationController.class);

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("/firestation")
    public FireStationResponseDTO getFirestationCoverage(@RequestParam int stationNumber) {
        LOGGER.info("Requête GET /firestation?stationNumber={} reçue", stationNumber);
        FireStationResponseDTO response = firestationService.getFirestationCoverage(stationNumber);
        LOGGER.info("Réponse GET /firestation: {} adultes, {} enfants", response.getAdultCount(), response.getChildCount());
        return response;
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> addFirestation(@RequestBody FirestationRequestDTO request) {
        LOGGER.info("Requête POST /firestation reçue: adresse='{}', station={}", request.getAddress(), request.getStation());
        try {
            Firestation created = firestationService.addFirestationMapping(request.getAddress(),request.getStation() );
            LOGGER.info("Mapping Firestation créé avec succès: {}", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Échec création mapping Firestation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody FirestationRequestDTO request) {
        try {
            Firestation updated = firestationService.updateFirestationMapping(

                    request.getAddress(),
                    request.getStation()
            );
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}