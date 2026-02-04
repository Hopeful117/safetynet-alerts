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
import java.util.Optional;
import java.util.Set;
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
     * @param stationNumbers List of fire station numbers
     * @return FloodResponseDTO containing households mapped by address with residents' details
     */
    @Override
    public FloodResponseDTO getFloodResponseByStationNumbers(List<Integer> stationNumbers) {
        Set<String> locations = firestationRepository.getAll().stream()
                .filter(fs -> stationNumbers.contains(fs.getStation()))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());
        log.info("Adresses couvertes par les stations {}: {}", stationNumbers, locations);
        return new FloodResponseDTO(locations.stream().collect(
                Collectors.toMap(
                        address -> address,
                        address -> {
                            List<Person> residents = personRepository.getAllByAddress(address);
                            log.info("{} résidents trouvés à l'adresse {}", residents.size(), address);
                            return residents.stream()
                                    .map(p -> {
                                        Optional<MedicalRecord> mr = medicalRecordRepository.findByFirstAndLastName(p.getFirstName(),p.getLastName());
                                        return new ResidentsDTO(p, mr);
                                    }).toList();
                        }
                )));

    }
}