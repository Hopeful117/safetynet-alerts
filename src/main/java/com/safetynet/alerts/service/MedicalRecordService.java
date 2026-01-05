package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
/**
 * Service interface for managing medical records.
 */
public interface MedicalRecordService {
    public boolean deleteMedicalRecord(String firstName, String lastName);
    public MedicalRecord addMedicalRecord(MedicalRecordDTO medicalRecordDTO);
    public MedicalRecord updateMedicalRecord(MedicalRecordDTO medicalRecordDTO);


}
