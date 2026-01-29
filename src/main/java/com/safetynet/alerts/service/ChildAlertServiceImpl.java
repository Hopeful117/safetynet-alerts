package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.HouseholdMemberDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        log.info("Recherche des enfants à l'adresse : {}", address);

        // Récupérer les personnes vivant à l'adresse donnée
        List<Person> residents = personRepository.getAllByAddress(address);


        log.debug("Nombre de résidents trouvés à l'adresse {}: {}", address, residents.size());

        List<ChildDTO> children = new ArrayList<>();
        List<HouseholdMemberDTO> adults = new ArrayList<>();

        personRepository.getAllByAddress(address)
                .forEach( person -> {
                            final Optional<MedicalRecord> medicalRecord = medicalRecordRepository.getAll()
                                    .stream()
                                    .filter(mr -> mr.getFirstName().equals(person.getFirstName()))
                                    .filter(mr -> mr.getLastName().equals(person.getLastName()))
                                    .findFirst();

                            medicalRecord.ifPresent(mr->{
                                int age = mr.calculateAge();

                                if (age < 18) {
                                    children.add(new ChildDTO(person.getFirstName(), person.getLastName(), age));
                                } else {
                                    adults.add(new HouseholdMemberDTO(person.getFirstName(), person.getLastName()));
                                }});

                            });
                        log.info("Enfants trouvés: {}, Adultes trouvés: {}", children.size(), adults.size());
                        return new ChildAlertResponseDTO(children, adults);
                            }


                        }
















