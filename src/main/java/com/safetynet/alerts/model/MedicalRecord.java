package com.safetynet.alerts.model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
/**
 * Model representing a medical record.
 */
@Data
@AllArgsConstructor
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;


    public int calculateAge(){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, FORMATTER);
        return Period.between(birthDate, LocalDate.now()).getYears();

    }
}
