package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO for community email response.
 * @param emails List of email addresses.
 *
 */
@Data
@AllArgsConstructor
public class CommunityEmailResponseDTO {
    private List<String> emails;

}
