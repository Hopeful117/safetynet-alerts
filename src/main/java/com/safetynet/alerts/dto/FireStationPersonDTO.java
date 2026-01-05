package com.safetynet.alerts.dto;

/**
 * DTO representing a person associated with a fire station.
 * Includes personal details such as first name, last name, address, and phone number.
 */
public class FireStationPersonDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;


    public FireStationPersonDTO(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
