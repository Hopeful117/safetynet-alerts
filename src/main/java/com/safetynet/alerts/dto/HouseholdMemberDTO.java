package com.safetynet.alerts.dto;

/**
 * DTO representing a household member with first and last name.
 */
public class HouseholdMemberDTO {

    private String firstName;
    private String lastName;

    public HouseholdMemberDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
