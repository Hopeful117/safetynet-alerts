package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;

import java.util.List;
import java.util.Optional;

public interface FirestationRepository {
    List<Firestation> getall();
    List <Firestation> getAllByStationNumber(int stationNumber);
    Optional<Firestation> findByAddress(String address);

}
