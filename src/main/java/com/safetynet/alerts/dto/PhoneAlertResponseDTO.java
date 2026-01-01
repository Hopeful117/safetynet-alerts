package com.safetynet.alerts.dto;

import java.util.List;
import java.util.Set;

public class PhoneAlertResponseDTO {

    private Set<String> phones;

    public PhoneAlertResponseDTO(Set<String> phones) {
        this.phones = phones;
    }

    public Set<String> getPhones() {
        return phones;
    }
}
