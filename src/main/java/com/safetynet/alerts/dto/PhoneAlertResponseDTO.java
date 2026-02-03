package com.safetynet.alerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
/**
 * DTO representing the response for a phone alert,
 * containing a set of phone numbers.
 */

@Data
@AllArgsConstructor
public class PhoneAlertResponseDTO {

    private Set<String> phones;


}
