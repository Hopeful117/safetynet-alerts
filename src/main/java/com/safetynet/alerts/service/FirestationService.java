package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationResponseDTO;


/**
 * Service interface for managing firestation-related operations.
 */
public interface FirestationService {
     FireStationResponseDTO getFirestationCoverage(int stationNumber);
     boolean addFirestationMapping( String address,int stationNumber);
     boolean updateFirestationMapping(String address,int stationNumber);
     boolean deleteFirestationMapping(String address);

}
