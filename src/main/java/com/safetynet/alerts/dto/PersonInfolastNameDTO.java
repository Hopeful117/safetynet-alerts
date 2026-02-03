package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 * DTO representing person information filtered by last name,
 * containing a list of residents with that last name.
 */
@Data
@AllArgsConstructor
public class PersonInfolastNameDTO {
    private List<ResidentsDTO> residents;

}
