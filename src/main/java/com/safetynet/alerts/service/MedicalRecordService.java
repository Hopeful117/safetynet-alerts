package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;

/**
 * Service interface for managing medical records.
 */
public interface MedicalRecordService {
    public boolean deleteMedicalRecord(String firstName, String lastName);

    public boolean addMedicalRecord(MedicalRecordDTO medicalRecordDTO);

    public boolean updateMedicalRecord(MedicalRecordDTO medicalRecordDTO);


}
