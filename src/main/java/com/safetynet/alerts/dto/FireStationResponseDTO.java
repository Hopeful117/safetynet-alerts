package com.safetynet.alerts.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO representing the response for a fire station query,
 * including a list of persons covered by the station
 */

@Data
@AllArgsConstructor
public class FireStationResponseDTO {
    private List <FireStationPersonDTO> persons;
    private int adultCount;
    private int childCount;

    @Data
    @AllArgsConstructor
    public static class FireStationPersonDTO {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;



    }


}
