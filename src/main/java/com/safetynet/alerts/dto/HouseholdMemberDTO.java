package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO representing a household member with first and last name.
 */

@Data
@AllArgsConstructor
public class HouseholdMemberDTO {

    private String firstName;
    private String lastName;


}
