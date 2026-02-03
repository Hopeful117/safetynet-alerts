package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing a person associated with a fire station.
 * Includes personal details such as first name, last name, address, and phone number.
 */
@Data
@AllArgsConstructor
public class FireStationPersonDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;



}
