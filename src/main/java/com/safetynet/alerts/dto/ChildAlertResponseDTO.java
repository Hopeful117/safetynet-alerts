package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO representing the response for a child alert,
 * containing lists of children and adults in a household.
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
