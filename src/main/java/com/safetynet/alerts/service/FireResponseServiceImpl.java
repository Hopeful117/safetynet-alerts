package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class FireResponseServiceImpl implements FireResponseService {
    private static final Logger LOGGER = LogManager.getLogger(FireResponseServiceImpl.class);
    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER= DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public FireResponseServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
    public FireResponseDTO getFireResponseByAddress(String address) {
        LOGGER.info("Recherche des résidents pour l'adresse {}", address);
        int stationNumber = repository.getFirestations().stream()
                .filter(fs -> fs.getAddress().equals(address))
                .map(fs -> fs.getStation())
                .findFirst()
                .orElseThrow(() ->

                new IllegalArgumentException("Aucune caserne trouvée pour cette adresse"));
        ;
        // Récupération des personnes à l'adresse donnée
        List<Person> residents = repository.getPersons().stream()
                .filter(p -> p.getAddress().equals(address))
                .toList();
        LOGGER.info("{} résidents trouvés à l'adresse {}", residents.size(), address);
        List<MedicalRecord> medicalRecords= repository.getMedicalRecords().stream().filter(mr->residents.stream().anyMatch(p->p.getFirstName().equals (mr.getFirstName()) && p.getLastName().equals(mr.getLastName()))).toList();
        // Transformation en DTO
        List<ResidentsDTO> residentDTOs = residents.stream()
                .map(p -> {
                    MedicalRecord mr = medicalRecords.stream()
                            .filter(record -> record.getFirstName().equals(p.getFirstName())
                                    && record.getLastName().equals(p.getLastName()))
                            .findFirst()
                            .orElse(null);
                    int age = 0;
                    List<String> medications = List.of();
                    List<String> allergies = List.of();
                    if (mr != null) {
                        LocalDate birthDate = LocalDate.parse(mr.getBirthdate(), FORMATTER);
                        age = Period.between(birthDate, LocalDate.now()).getYears();;
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
                })
                .toList();

       return new FireResponseDTO(residentDTOs, stationNumber);
    }

}
