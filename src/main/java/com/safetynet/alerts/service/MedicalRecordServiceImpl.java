package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
/**
 * Service implementation for managing medical records.
 */
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{
    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordServiceImpl.class);
    public MedicalRecordServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
    /**
     * Adds a new medical record.
     *
     * @param medicalRecordDTO The DTO containing medical record information.
     * @return The added MedicalRecord.
     */
    @Override
    public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        LOGGER.info("Ajout d'un nouveau dossier médical pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        MedicalRecord medicalRecord = new MedicalRecord(
                medicalRecordDTO.getFirstName(),
                medicalRecordDTO.getLastName(),
                medicalRecordDTO.getBirthdate(),
                medicalRecordDTO.getMedications(),
                medicalRecordDTO.getAllergies()
        );
        repository.getMedicalRecords().add(medicalRecord);
        LOGGER.debug("Dossier médical ajouté avec succès pour : {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return medicalRecord;
    }
    /**
     * Updates an existing medical record.
     *
     * @param medicalRecordDTO The DTO containing updated medical record information.
     * @return The updated MedicalRecord, or null if not found.
     */
    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        LOGGER.info("Mise à jour du dossier médical pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        MedicalRecord existingRecord = repository.getMedicalRecords().stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(medicalRecordDTO.getFirstName())
                        && mr.getLastName().equalsIgnoreCase(medicalRecordDTO.getLastName()))
                .findFirst()
                .orElse(null);
        if (existingRecord != null) {
            existingRecord.setBirthdate(medicalRecordDTO.getBirthdate());
            existingRecord.setMedications(medicalRecordDTO.getMedications());
            existingRecord.setAllergies(medicalRecordDTO.getAllergies());
            LOGGER.debug("Dossier médical mis à jour avec succès pour : {} {}", existingRecord.getFirstName(), existingRecord.getLastName());
            return existingRecord;
        } else {
            LOGGER.warn("Dossier médical non trouvé pour la mise à jour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            return null;
}
    }
    /**
     * Deletes a medical record.
     *
     * @param firstName The first name of the person.
     * @param lastName  The last name of the person.
     * @return true if the record was deleted, false if not found.
     */
    @Override
    public boolean deleteMedicalRecord(String firstName, String lastName) {
        LOGGER.info("Suppression du dossier médical pour : {} {}", firstName, lastName);
        MedicalRecord existingRecord = repository.getMedicalRecords().stream()
                .filter(mr -> mr.getFirstName().equalsIgnoreCase(firstName)
                        && mr.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
        if (existingRecord != null) {
            repository.getMedicalRecords().remove(existingRecord);
            LOGGER.debug("Dossier médical supprimé avec succès pour : {} {}", firstName, lastName);
            return true;
        } else {
            LOGGER.warn("Dossier médical non trouvé pour la suppression : {} {}", firstName, lastName);
            return false;
        }
    }
}
