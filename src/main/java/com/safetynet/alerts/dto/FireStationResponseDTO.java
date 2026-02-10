package com.safetynet.alerts.dto;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO representing the response for a fire station query,
 * including a list of persons covered by the station
 * and counts of adults and children based on their medical records.
 */

@Data
@AllArgsConstructor
public class FireStationResponseDTO {
    private List <FireStationPersonDTO> persons;
    private int adultCount;
    private int childCount;
    public FireStationResponseDTO(List<Person> coveredPersons, List<MedicalRecord> medicalRecords) {
        this.persons = coveredPersons.stream().map(FireStationPersonDTO::new).toList();
        this.adultCount = medicalRecords.stream().filter(m -> !m.isMinor()).toList().size();
        this.childCount = medicalRecords.stream().filter(MedicalRecord::isMinor).toList().size();
    }




    @Data
    @AllArgsConstructor
    public static class FireStationPersonDTO {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;

    public FireStationPersonDTO(Person person){
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();




    }


    }


}
