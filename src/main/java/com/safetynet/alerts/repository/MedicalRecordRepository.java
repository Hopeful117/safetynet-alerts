package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing medical records in the SafetyNet Alerts application.
 * Provides methods to retrieve, save, and delete medical records.
 */
public interface MedicalRecordRepository {
    List<MedicalRecord> getAll();

    Optional<MedicalRecord> findByFirstAndLastName(String firstname, String lastname);

    void save(MedicalRecord medicalRecord);

    void delete(MedicalRecord medicalRecord);
}
