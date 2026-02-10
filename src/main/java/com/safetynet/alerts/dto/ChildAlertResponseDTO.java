package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO representing the response for a child alert,
 * containing lists of children and adults in a household.
 * The constructor initializes the lists based on the provided medical records,
 * filtering minors as children and others as adults.
 * The ChildDTO class represents a child with their first name, last name, and age,
 * while the HouseholdMemberDTO class represents an adult household member with their first and last name.

 */
@Data
@AllArgsConstructor
public class ChildAlertResponseDTO {

    private List<ChildDTO> children = new ArrayList<>();
    private List<HouseholdMemberDTO> adults = new ArrayList<>();

    public ChildAlertResponseDTO(List<MedicalRecord> medicalRecords) {

        if (medicalRecords.stream().anyMatch(MedicalRecord::isMinor)) {

            children = medicalRecords.stream().filter(MedicalRecord::isMinor)
                    .map(ChildDTO::new)
                    .toList();

            adults = medicalRecords.stream().filter(m -> !m.isMinor())
                    .map(HouseholdMemberDTO::new)
                    .toList();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ChildDTO {
        private String firstName;
        private String lastName;
        private int age;

        public ChildDTO(MedicalRecord medicalRecord) {
            this.firstName = medicalRecord.getFirstName();
            this.lastName = medicalRecord.getLastName();
            this.age = medicalRecord.getAge();
        }
    }


    @Data
    @AllArgsConstructor
    public static class HouseholdMemberDTO {

        private String firstName;
        private String lastName;

        public HouseholdMemberDTO(MedicalRecord medicalRecord) {
            this.firstName = medicalRecord.getFirstName();
            this.lastName = medicalRecord.getLastName();


        }

    }
}
