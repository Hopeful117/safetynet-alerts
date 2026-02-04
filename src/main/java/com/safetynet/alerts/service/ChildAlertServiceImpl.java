package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/**
 * Service implementation for retrieving child alert information by address.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChildAlertServiceImpl implements ChildAlertService {

    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;


    /**
     * Retrieves child alert information for a given address.
     *
     * @param address The address to search for children and adults.
     * @return A ChildAlertResponseDTO containing lists of children and adults.
     */
    @Override
    public ChildAlertResponseDTO getChildAlertByAddress(final String address) {
        log.debug("Recherche des enfants à l'adresse : {}", address);


        final List<MedicalRecord> medicalRecords = personRepository.getAllByAddress(address)
                .stream()
                .map(person ->medicalRecordRepository.findByFirstAndLastName(person.getFirstName(), person.getLastName()) )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

//
//
        return new ChildAlertResponseDTO(medicalRecords);
    }


}
















