package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.FloodResponseService;
import com.safetynet.alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {
    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordController.class);
    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        try {
            LOGGER.info("Requête POST /medicalRecord reçue");
            MedicalRecord medicalRecord = service.addMedicalRecord(medicalRecordDTO);
            LOGGER.info("Dossier médical ajouté pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            return ResponseEntity.status(201).body(medicalRecord);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erreur lors de l'ajout du dossier médical: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        try {
            LOGGER.info("Requête PUT /medicalRecord reçue pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            MedicalRecord updatedRecord = service.updateMedicalRecord(medicalRecordDTO);
            if (updatedRecord == null) {
                return ResponseEntity.notFound().build();
            }
            LOGGER.info("Dossier médical mis à jour pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            return ResponseEntity.ok(updatedRecord);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erreur lors de la mise à jour du dossier médical: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            LOGGER.info("Requête DELETE /medicalRecord reçue pour : {} {}", firstName, lastName);
            boolean deleted = service.deleteMedicalRecord(firstName, lastName);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(204).build();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Erreur lors de la suppression du dossier médical: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}