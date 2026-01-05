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

/**
 * Contrôleur REST pour gérer les opérations liées aux Firestations.
 */
@RestController
public class FirestationController {
    private final FirestationService firestationService;
    private static final Logger LOGGER = LogManager.getLogger(FirestationController.class);

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }
/**
     * Récupère la couverture d'une station de pompiers donnée.
     *
     * @param stationNumber Le numéro de la station de pompiers.
     * @return Un DTO contenant le nombre d'adultes et d'enfants couverts par la station.
     */
    @GetMapping("/firestation")
    public FireStationResponseDTO getFirestationCoverage(@RequestParam int stationNumber) {
        LOGGER.info("Requête GET /firestation?stationNumber={} reçue", stationNumber);
        FireStationResponseDTO response = firestationService.getFirestationCoverage(stationNumber);
        LOGGER.info("Réponse GET /firestation: {} adultes, {} enfants", response.getAdultCount(), response.getChildCount());
        return response;
    }
/**
     * Ajoute un nouveau mapping Firestation.
     *
     * @param request Le DTO contenant l'adresse et le numéro de la station.
     * @return Le mapping Firestation créé.
     */
    @PostMapping("/firestation")
    public ResponseEntity<Firestation> addFirestation(@RequestBody FirestationRequestDTO request) {
        LOGGER.info("Requête POST /firestation reçue: adresse='{}', station={}", request.getAddress(), request.getStation());
        try {
            Firestation created = firestationService.addFirestationMapping(request.getAddress(), request.getStation());
            LOGGER.info("Mapping Firestation créé avec succès: {}", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Échec création mapping Firestation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
/**
     * Met à jour un mapping Firestation existant.
     *
     * @param request Le DTO contenant l'adresse et le nouveau numéro de la station.
     * @return Le mapping Firestation mis à jour.
     */
    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody FirestationRequestDTO request) {
        LOGGER.info("Requête PUT /firestation reçue: adresse='{}', station={}", request.getAddress(), request.getStation());
        try {
            Firestation updated = firestationService.updateFirestationMapping(

                    request.getAddress(),
                    request.getStation()
            );
            LOGGER.info("Mapping Firestation mis à jour avec succès: {}", updated);
            return ResponseEntity.ok(updated);

        } catch (IllegalArgumentException e) {
            LOGGER.error("Échec mise à jour mapping Firestation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
/**
     * Supprime un mapping Firestation existant.
     *
     * @param address L'adresse du mapping à supprimer.
     * @return Une réponse HTTP indiquant le résultat de l'opération.
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFirestation(@RequestParam String address) {
        LOGGER.info("Requête DELETE /firestation reçue: adresse='{}'", address);
        try {
            firestationService.deleteFirestationMapping(address);
            LOGGER.info("Mapping Firestation supprimé avec succès pour l'adresse '{}'", address);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Échec suppression mapping Firestation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}