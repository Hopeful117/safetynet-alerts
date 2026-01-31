package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;
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

/**
 * Service implementation for handling fire response information.
 */
@Slf4j
@RequiredArgsConstructor
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
        int stationNumber =station.get().getStation();

        // Récupération des personnes à l'adresse donnée
        List<Person> residents = personRepository.getAllByAddress(address);
        log.info("{} résidents trouvés à l'adresse {}", residents.size(), address);
        List<MedicalRecord> medicalRecords= medicalRecordRepository.getAll().stream().filter(mr->residents.stream()
                .anyMatch(p->p.getFirstName().equals (mr.getFirstName()) && p.getLastName().equals(mr.getLastName()))).toList();
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

                        age = mr.calculateAge();
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
