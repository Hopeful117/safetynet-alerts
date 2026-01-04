package com.safetynet.alerts.dto;

import java.util.List;

public class CommunityEmailResponseDTO {
    private List<String> emails;
    public CommunityEmailResponseDTO(List<String> emails) {
        this.emails = emails;
    }
    public List<String> getEmails() {
        return emails;
    }
}
