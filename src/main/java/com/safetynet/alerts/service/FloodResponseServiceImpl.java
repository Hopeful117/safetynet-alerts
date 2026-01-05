package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service implementation for handling flood response information.
 */
@Service
public class FloodResponseServiceImpl implements FloodResponseService {
    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final Logger LOGGER = LogManager.getLogger(FloodResponseServiceImpl.class);

    public FloodResponseServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
/**
     * Retrieves flood response information based on a list of fire station numbers.
     *
     * @param stationNumbers List of fire station numbers
     * @return FloodResponseDTO containing households mapped by address with residents' details
     */
    @Override
    public FloodResponseDTO getFloodResponseByStationNumbers(List<Integer> stationNumbers) {
        Set<String> locations = repository.getFirestations().stream()
                .filter(fs -> stationNumbers.contains(fs.getStation()))
                .map(fs -> fs.getAddress())
                .collect(Collectors.toSet());
        LOGGER.info("Adresses couvertes par les stations {}: {}", stationNumbers, locations);
        return new FloodResponseDTO(locations.stream().collect(
                java.util.stream.Collectors.toMap(
                        address -> address,
                        address -> {
                            List<Person> residents = repository.getPersons().stream()
                                    .filter(p -> p.getAddress().equals(address))
                                    .toList();
                            LOGGER.info("{} résidents trouvés à l'adresse {}", residents.size(), address);
                            return residents.stream()
                                    .map(p -> {
                                        var mrOpt = repository.getMedicalRecords().stream()
                                                .filter(record -> record.getFirstName().equals(p.getFirstName())
                                                        && record.getLastName().equals(p.getLastName()))
                                                .findFirst();
                                        int age = 0;
                                        List<String> medications = List.of();
                                        List<String> allergies = List.of();
                                        if (mrOpt.isPresent()) {
                                            var mr = mrOpt.get();
                                            var birthDate = java.time.LocalDate.parse(mr.getBirthdate(), FORMATTER);
                                            age = java.time.Period.between(birthDate, java.time.LocalDate.now()).getYears();
                                            medications = mr.getMedications();
                                            allergies = mr.getAllergies();
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