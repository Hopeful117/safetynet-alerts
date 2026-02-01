package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.SafetyNetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
                                        int age = 0;
                                        List<String> medications = List.of();
                                        List<String> allergies = List.of();
                                        if (mr.isPresent()) {

                                            age = mr.get().calculateAge();
                                            medications = mr.get().getMedications();
                                            allergies = mr.get().getAllergies();
                                        }
                                        return new ResidentsDTO(
                                                p.getFirstName(),
                                                p.getLastName(),
                                                p.getAddress(),
                                                p.getPhone(),
                                                age,
                                                medications,
                                                allergies
                                        );
                                    }).toList();
                        }
                )));

    }
}