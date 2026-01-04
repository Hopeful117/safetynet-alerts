package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PersonInfolastNameServiceImpl implements PersonInfolastNameService {
    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final Logger LOGGER = LogManager.getLogger(PersonInfolastNameServiceImpl.class);
    public PersonInfolastNameServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
    public PersonInfolastNameDTO getPersonInfoByLastName(String lastName) {
        List<ResidentsDTO> residentDTOs = repository.getPersons().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
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
                            allergies);
                })
                .toList();
        LOGGER.info("{} résidents trouvés avec le nom de famille {}", residentDTOs.size(), lastName);
        return new PersonInfolastNameDTO(residentDTOs);
    }

}
