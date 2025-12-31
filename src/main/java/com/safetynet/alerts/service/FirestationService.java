package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.dto.FirestationRequestDTO;
import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

import java.util.List;

public interface FirestationService {
     FireStationResponseDTO getFirestationCoverage(int stationNumber);
     Firestation addFirestationMapping( String address,int stationNumber);
     Firestation updateFirestationMapping(String address,int stationNumber);
     void deleteFirestationMapping(String address);
     PhoneAlertResponseDTO getPhoneAlertByStationNumber(int stationNumber);
}
