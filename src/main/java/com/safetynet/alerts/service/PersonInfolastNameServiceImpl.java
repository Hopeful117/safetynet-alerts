package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for retrieving person information by last name.
 */
@Slf4j
@RequiredArgsConstructor
@Service

public class PersonInfolastNameServiceImpl implements PersonInfolastNameService {
   private final PersonRepository personRepository;
   private final MedicalRecordRepository medicalRecordRepository;
    /**
     * Retrieves person information for all residents with the specified last name.
     *
     * @param lastName The last name to search for.
     * @return A ResidentsDTO containing the list of residents and their medical records.
     */
    @Override
    public ResidentsDTO getPersonInfoByLastName(String lastName) {
        final List<Person> residents = personRepository.getAll().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList();
        final List<MedicalRecord> records= medicalRecordRepository.getAll();






        return new ResidentsDTO(residents,records);
    }

}
