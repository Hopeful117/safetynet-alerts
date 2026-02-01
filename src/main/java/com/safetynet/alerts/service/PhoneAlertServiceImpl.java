package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.SafetyNetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Service implementation for retrieving phone alerts by fire station number.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneAlertServiceImpl implements PhoneAlertService {
   private final FirestationRepository firestationRepository;
   private final PersonRepository personRepository;
    /**
     * Retrieves a list of unique phone numbers for all residents covered by the specified fire station number.
     *
     * @param stationNumber The fire station number.
     * @return A PhoneAlertResponseDTO containing the list of phone numbers.
     */
    @Override
    public PhoneAlertResponseDTO getPhoneAlertByStationNumber(int stationNumber) {
        log.info("Recherche des numéros de téléphone pour la station numéro {}", stationNumber);

        // 1️⃣ Adresses couvertes par la station
        Set<String>addresses = firestationRepository.getAllByStationNumber(stationNumber)
                        .stream()
                        .map(Firestation::getAddress)
                                .collect(Collectors.toSet());


        log.debug("Adresses couvertes par la station {}: {}", stationNumber, addresses);

        // 2️⃣ Numéros de téléphone des personnes à ces adresses
        Set<String> phoneNumbers = personRepository.getAll().stream()
                        .filter(person->addresses.contains(person.getAddress()))
                                .map(Person::getPhone)
                                        .collect(Collectors.toSet());
        log.info("{} numéros de téléphone trouvés pour la station {}", phoneNumbers.size(), stationNumber);

        return new PhoneAlertResponseDTO(phoneNumbers);
    }
}
