package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class PhoneAlertServiceImpl implements PhoneAlertService {
    private final SafetyNetRepository repository;
    private static final Logger LOGGER = LogManager.getLogger(PhoneAlertServiceImpl.class);
    public PhoneAlertServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
    public PhoneAlertResponseDTO getPhoneAlertByStationNumber(int stationNumber) {
        LOGGER.info("Recherche des numéros de téléphone pour la station numéro {}", stationNumber);

        // 1️⃣ Adresses couvertes par la station
        List<String> addresses = repository.getFirestations().stream()
                .filter(fs -> fs.getStation() == stationNumber)
                .map(Firestation::getAddress)
                .collect(Collectors.toList());
        LOGGER.debug("Adresses couvertes par la station {}: {}", stationNumber, addresses);

        // 2️⃣ Numéros de téléphone des personnes à ces adresses
        Set<String> phoneNumbers = repository.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(Person::getPhone)
                .collect(Collectors.toSet());
        LOGGER.info("{} numéros de téléphone trouvés pour la station {}", phoneNumbers.size(), stationNumber);

        return new PhoneAlertResponseDTO(phoneNumbers);
    }
}
