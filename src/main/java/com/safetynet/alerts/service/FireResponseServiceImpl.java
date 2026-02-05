package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for handling fire response information.
 */
@Slf4j
@AllArgsConstructor
@Service
public class FireResponseServiceImpl implements FireResponseService {
    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;


    /**
     * Retrieves fire response information for a given address.
     *
     * @param address The address to retrieve fire response information for.
     * @return A FireResponseDTO containing residents and station number.
     */
    @Override
    public FireResponseDTO getFireResponseByAddress(String address) {
        log.info("Recherche des résidents pour l'adresse {}", address);
        Optional<Firestation> station = firestationRepository.findByAddress(address);
        if (station.isPresent()) {
            int stationNumber = station.get().getStation();

            // Récupération des personnes à l'adresse donnée
            List<Person> residents = personRepository.getAllByAddress(address);
            List<MedicalRecord> medicalRecords=medicalRecordRepository.getAll();
            ResidentsDTO residentDTOs = new ResidentsDTO(residents, medicalRecords);




            return new FireResponseDTO(residentDTOs, stationNumber);
        }

        return null;
    }





}
