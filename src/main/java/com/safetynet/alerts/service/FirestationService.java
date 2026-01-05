package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationResponseDTO;

import com.safetynet.alerts.model.Firestation;



/**
 * Service interface for managing firestation-related operations.
 */
public interface FirestationService {
     FireStationResponseDTO getFirestationCoverage(int stationNumber);
     Firestation addFirestationMapping( String address,int stationNumber);
     Firestation updateFirestationMapping(String address,int stationNumber);
     void deleteFirestationMapping(String address);

}
