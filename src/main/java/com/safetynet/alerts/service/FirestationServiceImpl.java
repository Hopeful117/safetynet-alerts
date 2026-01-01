package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.model.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(FirestationServiceImpl.class);

    public FirestationServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }

    @Override
    public FireStationResponseDTO getFirestationCoverage(int stationNumber) {
        LOGGER.info("Calcul couverture pour la station numéro {}", stationNumber);

        // 1️⃣ Adresses couvertes par la station
        Set<String> addresses = repository.getFirestations().stream()
                .filter(fs -> fs.getStation() == stationNumber)
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());
        LOGGER.debug("Adresses couvertes par la station {}: {}", stationNumber, addresses);

        // 2️⃣ Personnes habitant à ces adresses
        List<Person> coveredPersons = repository.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .toList();
        LOGGER.info("{} personnes trouvées pour la station {}", coveredPersons.size(), stationNumber);

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
                    LOGGER.debug("Enfant trouvé: {} {} ({} ans)", person.getFirstName(), person.getLastName(), age);
                } else {
                    adultCount++;
                    LOGGER.debug("Adulte trouvé: {} {} ({} ans)", person.getFirstName(), person.getLastName(), age);
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
        LOGGER.info("Couverture calculée: {} adultes, {} enfants", adultCount, childCount);
        return new FireStationResponseDTO(personDTOs, adultCount, childCount);
    }

    private int calculateAge(String birthdate) {

        LocalDate birth = LocalDate.parse(birthdate, FORMATTER);
        return Period.between(birth, LocalDate.now()).getYears();
    }
    public Firestation addFirestationMapping(String address, int station) {
        // Vérifie si l'adresse existe déjà
        LOGGER.info("Tentative d'ajout d'un mapping Firestation: adresse='{}', station={}", address, station);

        Optional<Firestation> existing = repository.getFirestations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .findFirst();

        if (existing.isPresent()) {
            LOGGER.error("Échec de l'ajout: l'adresse '{}' existe déjà avec la station {}", address, existing.get().getStation());
            throw new IllegalArgumentException("Cette adresse a déjà un mapping.");

        }

        Firestation newMapping = new Firestation(address, station);
        repository.getFirestations().add(newMapping);
        LOGGER.info("Mapping Firestation ajouté avec succès: {}", newMapping);
        return newMapping;
    }

    public Firestation updateFirestationMapping( String address,int station) {
        LOGGER.info("Tentative de mise à jour Firestation: adresse='{}', nouvelle station={}", address, station);

        Firestation firestation = repository.getFirestations().stream()
                .filter(fs -> fs.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.error("Aucune Firestation trouvée pour l'adresse '{}'", address);
                    return new IllegalArgumentException("Adresse introuvable");
                });

        int oldStation = firestation.getStation();
        firestation.setStation(station);

        LOGGER.info("Firestation mise à jour: adresse='{}', station {} → {}", address, oldStation, station);
        return firestation;
    }
    public void deleteFirestationMapping(String address) {
        LOGGER.info("Tentative de suppression Firestation pour l'adresse '{}'", address);

        boolean removed = repository.getFirestations().removeIf(
                fs -> fs.getAddress().equalsIgnoreCase(address)
        );

        if (!removed) {
            LOGGER.error("Suppression impossible: aucune Firestation trouvée pour '{}'", address);
            throw new IllegalArgumentException("Adresse introuvable");
        }

        LOGGER.info("Firestation supprimée avec succès pour l'adresse '{}'", address);
    }



}
