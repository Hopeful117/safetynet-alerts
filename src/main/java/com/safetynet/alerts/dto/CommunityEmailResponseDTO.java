package com.safetynet.alerts.dto;

import java.util.List;

/**
 * DTO for community email response.
 * @param emails List of email addresses.
 *
 */
public class CommunityEmailResponseDTO {
    private List<String> emails;
    public CommunityEmailResponseDTO(List<String> emails) {
        this.emails = emails;
    }
    public List<String> getEmails() {
        return emails;
    }
}
