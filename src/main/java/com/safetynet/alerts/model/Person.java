package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Model class representing a person with personal details.
 */
@Data
@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;


}
