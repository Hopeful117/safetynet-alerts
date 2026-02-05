package com.safetynet.alerts.service;

import java.util.List;
import java.util.Optional;




import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

/**
 * Service implementation for firestation-related operations.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FirestationServiceImpl implements FirestationService {
    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;




/**
     * Retrieves coverage information for a specific fire station number.
     *
     * @param stationNumber The fire station number.
     * @return A FireStationResponseDTO containing the list of persons covered
     *         by the station, along with counts of adults and children.
     */
    @Override
    public FireStationResponseDTO getFirestationCoverage(int stationNumber) {
        log.debug("Calcul couverture pour la station numéro {}", stationNumber);



        List<String> addresses = firestationRepository.getAllByStationNumber(stationNumber)
                        .stream().map(Firestation::getAddress).toList();



        log.debug("Adresses couvertes par la station {}: {}", stationNumber, addresses);


        List<Person> coveredPersons = personRepository.getAll().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
        log.debug("{} personnes trouvées pour la station {}", coveredPersons.size(), stationNumber);


        List<MedicalRecord> medicalRecords = coveredPersons.stream()
                .map(p -> medicalRecordRepository.findByFirstAndLastName(p.getFirstName(), p.getLastName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();


        return new FireStationResponseDTO(coveredPersons, medicalRecords);
            }


            /**
     * Adds a new firestation mapping.
     *
     * @param address The address to map.
     * @param station The fire station number.
     * @return The newly created Firestation mapping.
     * @throws IllegalArgumentException if the address already has a mapping.
     */


    @Override
    public boolean addFirestationMapping(String address, int station) {
        // Vérifie si l'adresse existe déjà
        log.info("Tentative d'ajout d'un mapping Firestation: adresse='{}', station={}", address, station);

        Optional<Firestation> existing = firestationRepository.findByAddress(address);


        if (existing.isPresent()) {
            log.error("Échec de l'ajout: l'adresse '{}' existe déjà avec la station {}", address, existing.get().getStation());
            return false;

        }

        Firestation newMapping = new Firestation(address, station);
        firestationRepository.save(newMapping);
        log.info("Mapping Firestation ajouté avec succès: {}", newMapping);
        return true;
    }
    /**
     * Updates an existing firestation mapping.
     *
     * @param address The address to update.
     * @param station The new fire station number.
     * @return The updated Firestation mapping.
     * @throws IllegalArgumentException if the address does not exist.
     */
    @Override
    public boolean updateFirestationMapping(String address, int station) {
        log.info("Tentative de mise à jour Firestation: adresse='{}', nouvelle station={}", address, station);

        Optional<Firestation> firestation =firestationRepository.findByAddress(address);
                if (firestation.isEmpty()) {
                    log.error("Aucune Firestation trouvée pour l'adresse '{}'", address);
                    return false;
                }

        int oldStation = firestation.get().getStation();
        firestation.get().setStation(station);

        log.info("Firestation mise à jour: adresse='{}', station {} → {}", address, oldStation, station);
        return true;
    }

    /**
     * Deletes a firestation mapping by address.
     *
     * @param address The address of the mapping to delete.
     * @return
     * @throws IllegalArgumentException if the address does not exist.
     */
    @Override
    public boolean deleteFirestationMapping(String address) {
        log.info("Tentative de suppression Firestation pour l'adresse '{}'", address);
        Optional<Firestation> station = firestationRepository.findByAddress(address);
        if(station.isEmpty()) {
            log.error("Suppression impossible: aucune Firestation trouvée pour '{}'", address);
            return false;
        }


        firestationRepository.delete(station.get());
        log.info("Firestation supprimée avec succès pour l'adresse '{}'", address);
        return true;
    }



}
