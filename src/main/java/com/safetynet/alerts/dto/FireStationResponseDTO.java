package com.safetynet.alerts.dto;
import java.util.List;

/**
 * DTO representing the response for a fire station query,
 * including a list of persons covered by the station
 */
public class FireStationResponseDTO {
    private List <FireStationPersonDTO> persons;
    private int adultCount;
    private int childCount;
    public FireStationResponseDTO() {
    }
    public FireStationResponseDTO(List<FireStationPersonDTO> persons, int adultCount, int childCount) {
        this.persons = persons;
        this.adultCount = adultCount;
        this.childCount = childCount;
    }
    public void setPersons(List<FireStationPersonDTO> persons) {
        this.persons = persons;
    }
    public List<FireStationPersonDTO> getPersons() {
        return persons;
}
    public int getAdultCount() {
        return adultCount;
    }
    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }
    public int getChildCount() {
        return childCount;
    }
    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
}
