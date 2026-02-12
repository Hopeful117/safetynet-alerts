package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service implementation for handling flood response information.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FloodResponseServiceImpl implements FloodResponseService {
    private final FirestationRepository firestationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;


    /**
     * Retrieves flood response information based on a list of fire station numbers.
     *
     * @param stationNumber List of fire station numbers
     * @return FloodResponseDTO containing the addresses covered by the specified fire stations and the residents at those addresses
     */
    @Override
    public FloodResponseDTO getFloodResponseByStationNumbers(Set<Integer> stationNumber) {
        Set<String> locations = firestationRepository.getAll()
                .stream()
                .filter(fs -> stationNumber.contains(fs.getStation()))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        log.debug("Adresses couvertes par les stations {}: {}", stationNumber, locations);
        return new FloodResponseDTO(
                locations.stream()
                        .collect(Collectors.toMap(
                                Function.identity(),
                                address -> {
                                    List<Person> residents = personRepository.getAllByAddress(address);
                                    List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll().stream()
                                            .filter(mr -> residents.stream()
                                                    .anyMatch(p -> p.getFirstName().trim().equalsIgnoreCase(mr.getFirstName()) && p.getLastName().trim().equalsIgnoreCase(mr.getLastName())))
                                            .toList();
                                    return new ResidentsDTO(residents, medicalRecords).getResidents();
                                }
                        ))
        );


    }
}