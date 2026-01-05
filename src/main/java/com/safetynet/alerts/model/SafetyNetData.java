package com.safetynet.alerts.model;
import java.util.List;
/**
 * Class representing the data structure for SafetyNet,
 * containing lists of persons, firestations, and medical records.
 */
import com.fasterxml.jackson.annotation.JsonProperty;

public class SafetyNetData {
    private List <Person>persons;
    private List <Firestation>firestations;

    @JsonProperty("medicalrecords")
    private List <MedicalRecord>medicalRecords;
    public SafetyNetData() {
}
    public List<Person> getPersons() {
        return persons;
    }
    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
    public List<Firestation> getFirestations() {
        return firestations;
    }
    public void setFirestations(List<Firestation> firestations) {
        this.firestations = firestations;
    }
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
    public void setMedicalRecords(List<MedicalRecord> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }
}
