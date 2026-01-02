package com.safetynet.alerts.dto;

import com.safetynet.alerts.model.MedicalRecord;

import java.util.List;

public class ResidentsDTO {
    private String firstName;
    private String  lastName;
    private String address;
    private String phone;
    private int age;
    private List<String> medications;
    private List <String> allergies;
    public ResidentsDTO(String firstName, String lastName, String address, String phone, int age, List <String> medications, List <String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.medications=medications;
        this.allergies=allergies;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public int getAge() {
        return age;
    }
   public List<String> getMedications() {
        return medications;
    }
    public List<String> getAllergies() {
        return allergies;
    }

}
