package com.safetynet.alerts.dto;

/**
 * DTO representing a child with first name, last name, and age.
 * Used for transferring child data in responses.
 */
public class ChildDTO {

    private String firstName;
    private String lastName;
    private int age;

    public ChildDTO(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}