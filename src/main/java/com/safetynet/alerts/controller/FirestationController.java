package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.dto.FirestationRequestDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {
    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("/firestation")
    public FireStationResponseDTO getFirestationCoverage(@RequestParam int stationNumber) {

        return firestationService.getFirestationCoverage(stationNumber);
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> addFirestation(@RequestBody FirestationRequestDTO request) {
        try {
            Firestation created = firestationService.addFirestationMapping(request.getStation(), request.getAddress() );
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
}