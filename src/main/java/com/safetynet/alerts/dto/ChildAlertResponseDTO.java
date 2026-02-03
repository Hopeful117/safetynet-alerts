package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO representing the response for a child alert,
 * containing lists of children and adults in a household.
 */
@Data
@AllArgsConstructor
public class ChildAlertResponseDTO {

    private List<ChildDTO> children;
    private List<HouseholdMemberDTO> adults;


}
