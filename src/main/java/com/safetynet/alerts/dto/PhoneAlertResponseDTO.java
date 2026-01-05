package com.safetynet.alerts.dto;


import java.util.Set;
/**
 * DTO representing the response for a phone alert,
 * containing a set of phone numbers.
 */
public class PhoneAlertResponseDTO {

    private Set<String> phones;

    public PhoneAlertResponseDTO(Set<String> phones) {
        this.phones = phones;
    }

    public Set<String> getPhones() {
        return phones;
    }
}
