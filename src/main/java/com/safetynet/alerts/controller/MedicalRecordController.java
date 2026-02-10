package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.FloodResponseService;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * Controller pour gérer les requêtes liées aux dossiers médicaux.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class MedicalRecordController {

    private final MedicalRecordService service;


    /**
     * Gère les requêtes POST pour ajouter un nouveau dossier médical.
     *
     * @param medicalRecordDTO le DTO du dossier médical à ajouter.
     * @return une réponse HTTP avec le dossier médical ajouté ou une erreur.
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {

        log.info("Requête POST /medicalRecord reçue");
        try {
            boolean medicalRecord = service.addMedicalRecord(medicalRecordDTO);
            if (medicalRecord) {
                MedicalRecord medicalRecord1 = new MedicalRecord(medicalRecordDTO.getFirstName(),
                        medicalRecordDTO.getLastName(),
                        medicalRecordDTO.getBirthdate(),
                        medicalRecordDTO.getMedications(),
                        medicalRecordDTO.getAllergies());
                log.info("Dossier médical ajouté pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
                return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord1);
            }

            log.error("Erreur lors de l'ajout du dossier médical");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

        } catch (Exception e) {
            log.error("Exception lors de l'ajout du dossier médical: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gère les requêtes PUT pour mettre à jour un dossier médical existant.
     *
     * @param medicalRecordDTO le DTO du dossier médical à mettre à jour.
     * @return une réponse HTTP avec le dossier médical mis à jour ou une erreur.
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {

        log.info("Requête PUT /medicalRecord reçue pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        try {
            boolean updatedRecord = service.updateMedicalRecord(medicalRecordDTO);
            if (updatedRecord) {
                log.info("Dossier médical mis à jour pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
                MedicalRecord medicalRecord = new MedicalRecord(medicalRecordDTO.getFirstName(),
                        medicalRecordDTO.getLastName(),
                        medicalRecordDTO.getBirthdate(),
                        medicalRecordDTO.getMedications(),
                        medicalRecordDTO.getAllergies());
                return ResponseEntity.accepted().body(medicalRecord);
            }
            log.error("Dossier médical non trouvé pour mise à jour: {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error("Exception lors de la mise à jour du dossier médical: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Gère les requêtes DELETE pour supprimer un dossier médical existant.
     * @param firstName le prénom de la personne dont le dossier médical doit être supprimé.
     * @param lastName le nom de famille de la personne dont le dossier médical doit être
     * @return une réponse HTTP indiquant le résultat de la suppression du dossier médical.
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {

        log.info("Requête DELETE /medicalRecord reçue pour : {} {}", firstName, lastName);
        try {
            boolean deleted = service.deleteMedicalRecord(firstName, lastName);
            if (deleted) {
                return ResponseEntity.noContent().build();
            }


            log.error("Erreur lors de la suppression du dossier médical: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Exception lors de la suppression du dossier médical: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
