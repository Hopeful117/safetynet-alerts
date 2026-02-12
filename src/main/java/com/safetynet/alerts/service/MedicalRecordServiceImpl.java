package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for managing medical records.
 */
@Slf4j
@RequiredArgsConstructor
@Service

public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;


    /**
     * Adds a new medical record.
     *
     * @param medicalRecordDTO The DTO containing medical record information.
     * @return true if the record was added successfully, false if a record with the same name already exists.
     */
    @Override
    public boolean addMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        log.debug("Ajout d'un nouveau dossier médical pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        Optional<MedicalRecord> exists = medicalRecordRepository.findByFirstAndLastName(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        if (exists.isPresent()) {
            log.error("Dossier médical déjà présent");
            return false;
        }
        MedicalRecord medicalRecord = new MedicalRecord(
                medicalRecordDTO.getFirstName(),
                medicalRecordDTO.getLastName(),
                medicalRecordDTO.getBirthdate(),
                medicalRecordDTO.getMedications(),
                medicalRecordDTO.getAllergies()
        );
        medicalRecordRepository.save(medicalRecord);
        log.debug("Dossier médical ajouté avec succès pour : {} {}", medicalRecord.getFirstName(), medicalRecord.getLastName());
        return true;
    }

    /**
     * Updates an existing medical record.
     *
     * @param medicalRecordDTO The DTO containing updated medical record information.
     * @return true if the record was updated successfully, false if the record was not found.
     */
    @Override
    public boolean updateMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        log.debug("Mise à jour du dossier médical pour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        Optional<MedicalRecord> existingRecord = medicalRecordRepository.findByFirstAndLastName(medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
        if (existingRecord.isPresent()) {
            existingRecord.get().setBirthdate(medicalRecordDTO.getBirthdate());
            existingRecord.get().setMedications(medicalRecordDTO.getMedications());
            existingRecord.get().setAllergies(medicalRecordDTO.getAllergies());
            log.debug("Dossier médical mis à jour avec succès pour : {} {}", existingRecord.get().getFirstName(), existingRecord.get().getLastName());
            return true;
        } else {
            log.error("Dossier médical non trouvé pour la mise à jour : {} {}", medicalRecordDTO.getFirstName(), medicalRecordDTO.getLastName());
            return false;
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
        log.debug("Suppression du dossier médical pour : {} {}", firstName, lastName);

        Optional<MedicalRecord> existingRecord = medicalRecordRepository.findByFirstAndLastName(firstName, lastName);
        if (existingRecord.isPresent()) {
            medicalRecordRepository.delete(existingRecord.get());
            log.debug("Dossier médical supprimé avec succès pour : {} {}", firstName, lastName);
            return true;
        } else {
            log.error("Dossier médical non trouvé pour la suppression : {} {}", firstName, lastName);
            return false;
        }
    }
}
