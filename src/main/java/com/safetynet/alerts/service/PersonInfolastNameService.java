package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;
import com.safetynet.alerts.dto.ResidentsDTO;

import java.util.List;

public interface PersonInfolastNameService {
    public PersonInfolastNameDTO getPersonInfoByLastName(String lastName);
}
