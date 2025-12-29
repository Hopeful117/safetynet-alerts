package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.safetynet.alerts.dto.FirestationRequestDTO;
import com.safetynet.alerts.model.Firestation;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.FireStationPersonDTO;
import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;

@Service
public class FirestationServiceImpl implements FirestationService {

    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public FirestationServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }

    @Override
    public FireStationResponseDTO getFirestationCoverage(int stationNumber) {

        // 1️⃣ Adresses couvertes par la station
        Set<String> addresses = repository.getFirestations().stream()
                .filter(fs -> fs.getStation() == stationNumber)
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        // 2️⃣ Personnes habitant à ces adresses
        List<Person> coveredPersons = repository.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();

        // 3️⃣ Calcul adultes / enfants
        int adultCount = 0;
        int childCount = 0;

        for (Person person : coveredPersons) {
            MedicalRecord record = repository.getMedicalRecords().stream()
                    .filter(mr -> mr.getFirstName().equals(person.getFirstName())
                            && mr.getLastName().equals(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                int age = calculateAge(record.getBirthdate());
                if (age < 18) {
                    childCount++;
                } else {
                    adultCount++;
                }
            }
        }

        // 4️⃣ Construction de la liste DTO personnes
        List<FireStationPersonDTO> personDTOs = coveredPersons.stream()
                .map(p -> new FireStationPersonDTO(
                        p.getFirstName(),
                        p.getLastName(),
                        p.getAddress(),
                        p.getPhone()))
                .collect(Collectors.toList());

        return new FireStationResponseDTO(personDTOs, adultCount, childCount);
    }

    private int calculateAge(String birthdate) {
        LocalDate birth = LocalDate.parse(birthdate, FORMATTER);
        return Period.between(birth, LocalDate.now()).getYears();
    }
    public Firestation addFirestationMapping(int station,String address) {
        // Vérifie si l'adresse existe déjà
        Optional<Firestation> existing = repository.getFirestations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .findFirst();

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Cette adresse a déjà un mapping.");
        }

        Firestation newMapping = new Firestation(address, station);
        repository.getFirestations().add(newMapping);
        return newMapping;
    }

}
