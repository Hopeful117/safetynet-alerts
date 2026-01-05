package com.safetynet.alerts.dto;

import java.util.List;

/**
 * DTO representing the response for a child alert,
 * containing lists of children and adults in a household.
 */
public class ChildAlertResponseDTO {

    private List<ChildDTO> children;
    private List<HouseholdMemberDTO> adults;

    public ChildAlertResponseDTO(List<ChildDTO> children,
                                 List<HouseholdMemberDTO> adults) {
        this.children = children;
        this.adults = adults;
    }

    public List<ChildDTO> getChildren() {
        return children;
    }

    public List<HouseholdMemberDTO> getAdults() {
        return adults;
    }
}
