package com.safetynet.alerts.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * DTO representing a resident's information,
 * including personal details and medical records.
 */

@Data
@AllArgsConstructor
public class ResidentsDTO {
    private String firstName;
    private String  lastName;
    private String address;
    private String phone;
    private int age;
    private List<String> medications;
    private List <String> allergies;


}
