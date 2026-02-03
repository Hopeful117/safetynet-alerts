package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing a child with first name, last name, and age.
 * Used for transferring child data in responses.
 */
@Data
@AllArgsConstructor
public class ChildDTO {

    private String firstName;
    private String lastName;
    private int age;


}