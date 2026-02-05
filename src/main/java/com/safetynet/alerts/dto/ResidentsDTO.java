package com.safetynet.alerts.dto;



import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * DTO representing a resident's information,
 * including personal details and medical records.
 */

@Data
@AllArgsConstructor
public class ResidentsDTO {
    private List<Resident> residents;

    public ResidentsDTO(List<Person> persons, List<MedicalRecord> medicalRecords) {
        this.residents = persons.stream()
                .map(p -> new Resident(p, medicalRecords.stream()
                        .filter(m -> m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName()))
                        .findFirst()))
                .toList();
    }

    @Data
    @AllArgsConstructor
    public static class Resident {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;

        public Resident(Person p, Optional<MedicalRecord> record) {
            this.firstName = p.getFirstName();
            this.lastName = p.getLastName();
            this.address = p.getAddress();
            this.phone = p.getPhone();
            if (record.isPresent()) {
                this.age = record.get().getAge();
                this.medications = record.get().getMedications();
                this.allergies = record.get().getAllergies();
            }
        }


    }
}
