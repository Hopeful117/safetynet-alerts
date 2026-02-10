package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * @return A set of unique phone numbers for residents covered by the fire station.
     */
    @Override
    public Set<String> getPhoneAlertByStationNumber(int stationNumber) {
        log.debug("Recherche des numéros de téléphone pour la station numéro {}", stationNumber);


        Set<String>addresses = firestationRepository.getAllByStationNumber(stationNumber)
                        .stream()
                        .map(Firestation::getAddress)
                                .collect(Collectors.toSet());


        log.debug("Adresses couvertes par la station {}: {}", stationNumber, addresses);


        Set<String> phoneNumbers = personRepository.getAll().stream()
                        .filter(person->addresses.contains(person.getAddress()))
                                .map(Person::getPhone)
                                        .collect(Collectors.toSet());
        log.info("{} numéros de téléphone trouvés pour la station {}", phoneNumbers.size(), stationNumber);

        return phoneNumbers;
    }
}
