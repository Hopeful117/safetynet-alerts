package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Implementation of the FirestationRepository interface that interacts with the SafetyNetRepository to manage fire station data.
 */


/**
 * The FirestationRepositoryImpl class provides methods to retrieve, save, and delete fire station information. It uses the SafetyNetRepository to access the underlying data storage for fire stations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FirestationRepositoryImpl implements FirestationRepository {
    final SafetyNetRepository safetyNetRepository;

    /**
     * Retrieves all fire stations from the repository.
     *
     * @return a list of all fire stations
     */
    @Override
    public List<Firestation> getAll() {
        return safetyNetRepository.getFirestations();
    }

    /**
     * Retrieves all fire stations that match the specified station number.
     *
     * @param stationNumber the station number to filter by
     * @return a list of fire stations with the specified station number
     */
    @Override
    public List<Firestation> getAllByStationNumber(int stationNumber) {
        return safetyNetRepository.getFirestations().stream()
                .filter(firestation -> stationNumber == firestation.getStation())
                .toList();
    }

    /**
     * Finds a fire station by its address.
     *
     * @param address the address of the fire station to find
     * @return an Optional containing the fire station if found, or empty if not found
     */
    @Override
    public Optional<Firestation> findByAddress(String address) {
        return safetyNetRepository.getFirestations().stream()
                .filter(firestation -> address.trim().equalsIgnoreCase(firestation.getAddress()))
                .findFirst();
    }

    /**
     * Saves a fire station to the repository.
     *
     * @param firestation the fire station to save
     */
    @Override
    public void save(Firestation firestation) {
        getAll().add(firestation);
    }

    /**
     * Deletes a fire station from the repository.
     *
     * @param firestation the fire station to delete
     */
    @Override
    public void delete(Firestation firestation) {
        getAll().remove(firestation);
    }

}
