package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Class representing the data structure for SafetyNet,
 * containing lists of persons, firestations, and medical records.
 */
@Data
@AllArgsConstructor
public class SafetyNetData {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;
}
