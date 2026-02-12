package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;

/**
 * Repository interface for accessing SafetyNet data.
 * represents a contract for retrieving persons, firestations, and medical records.
 */

public interface SafetyNetRepository {
    List<Person> getPersons();

    List<Firestation> getFirestations();

    List<MedicalRecord> getMedicalRecords();
}
