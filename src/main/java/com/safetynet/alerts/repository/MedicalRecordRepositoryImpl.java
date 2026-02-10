package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the MedicalRecordRepository interface that interacts with the SafetyNetRepository to manage medical records.
 */


/**
 * The MedicalRecordRepositoryImpl class provides methods to retrieve, save, and delete medical records. It uses the SafetyNetRepository to access the underlying data storage for medical records.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository{
    final SafetyNetRepository safetyNetRepository;


    /**
     * Retrieves all medical records from the SafetyNetRepository.
     *
     * @return a list of all medical records
     */
    @Override
    public List<MedicalRecord> getAll() {
        return safetyNetRepository.getMedicalRecords();
    }

    /**
     * Finds a medical record by the first name and last name.
     *
     * @param firstname the first name of the person
     * @param lastname  the last name of the person
     * @return an Optional containing the found medical record, or empty if not found
     */
    @Override
    public Optional<MedicalRecord> findByFirstAndLastName(String firstname,String lastname) {
        return safetyNetRepository.getMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstname))
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastname))
                .findFirst();

    }

    /**
     * Saves a medical record to the SafetyNetRepository.
     *
     * @param medicalRecord the medical record to be saved
     */
    @Override
    public void save(MedicalRecord medicalRecord){
        getAll().add(medicalRecord);
    }

    /**
     * Deletes a medical record from the SafetyNetRepository.
     *
     * @param medicalRecord the medical record to be deleted
     */
    @Override
    public void delete(MedicalRecord medicalRecord){
        getAll().remove(medicalRecord);
    }
}
