package com.safetynet.alerts.model;

import java.util.List;

import lombok.Data;

/**
 * Class representing the data structure for SafetyNet,
 * containing lists of persons, firestations, and medical records.
 */
@Data
public class SafetyNetData {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;
}
