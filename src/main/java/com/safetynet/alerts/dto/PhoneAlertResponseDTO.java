package com.safetynet.alerts.dto;

import java.util.List;

public class PhoneAlertResponseDTO {

    private List<String> phones;

    public PhoneAlertResponseDTO(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getPhones() {
        return phones;
    }
}
