package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.dto.ChildDTO;
import com.safetynet.alerts.dto.HouseholdMemberDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChildAlertServiceImpl implements ChildAlertService {
    private static final Logger LOGGER = LogManager.getLogger(ChildAlertServiceImpl.class);
    private final SafetyNetRepository repository;
    private static final DateTimeFormatter FORMATTER= DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public ChildAlertServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
    @Override
    public ChildAlertResponseDTO getChildAlertByAddress(String address) {
        LOGGER.info("Recherche des enfants à l'adresse : {}", address);

        // Récupérer les personnes vivant à l'adresse donnée
        List<Person> residents = repository.getPersons().stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .toList();

        LOGGER.debug("Nombre de résidents trouvés à l'adresse {}: {}", address, residents.size());

        List<ChildDTO> children = new ArrayList<>();
        List<HouseholdMemberDTO> adults = new ArrayList<>();

        for (Person person : residents) {
            MedicalRecord record = repository.getMedicalRecords().stream()
                    .filter(mr -> mr.getFirstName().equals(person.getFirstName())
                            && mr.getLastName().equals(person.getLastName()))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                LocalDate birthDate = LocalDate.parse(record.getBirthdate(), FORMATTER);
                int age = Period.between(birthDate, LocalDate.now()).getYears();

                if (age < 18) {
                    children.add(new ChildDTO(person.getFirstName(), person.getLastName(), age));
                } else {
                    adults.add(new HouseholdMemberDTO(person.getFirstName(), person.getLastName()));
                }
            }
        }

        LOGGER.info("Enfants trouvés: {}, Adultes trouvés: {}", children.size(), adults.size());

        return new ChildAlertResponseDTO(children, adults);
    }
}
