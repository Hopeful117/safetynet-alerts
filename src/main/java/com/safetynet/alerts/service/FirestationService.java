package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.dto.FirestationRequestDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface FirestationService {
     FireStationResponseDTO getFirestationCoverage(int stationNumber);
     Firestation addFirestationMapping(int stationNumber, String address);
}
