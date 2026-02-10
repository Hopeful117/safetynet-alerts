package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;

import java.util.List;
import java.util.Optional;
/**
 * Interface for managing Firestation data.
 * Provides methods to retrieve, save, and delete firestation information.
 */

public interface FirestationRepository {
    List<Firestation> getAll();
    List <Firestation> getAllByStationNumber(int stationNumber);
    Optional<Firestation> findByAddress(String address);
    void  save(Firestation firestation);
    void delete(Firestation firestation);

}
