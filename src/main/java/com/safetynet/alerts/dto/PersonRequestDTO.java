package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing a person request with personal details.
 * Used for transferring person data in requests.
 */

@Data
@AllArgsConstructor
public class PersonRequestDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

}
