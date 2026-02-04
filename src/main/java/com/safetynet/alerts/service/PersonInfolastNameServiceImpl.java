package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * @return A PersonInfolastNameDTO containing a list of residents with that last name.
     */
    @Override
    public PersonInfolastNameDTO getPersonInfoByLastName(String lastName) {
        List<ResidentsDTO> residentDTOs = personRepository.getAll().stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .map(p -> {
                   Optional<MedicalRecord> medicalRecord= medicalRecordRepository.findByFirstAndLastName(p.getFirstName(), p.getLastName());

                    return new ResidentsDTO(p,medicalRecord);


                })
                .toList();
        log.info("{} résidents trouvés avec le nom de famille {}", residentDTOs.size(), lastName);
        return new PersonInfolastNameDTO(residentDTOs);
    }

}
