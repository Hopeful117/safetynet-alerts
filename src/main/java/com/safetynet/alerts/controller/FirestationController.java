package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.service.FirestationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
