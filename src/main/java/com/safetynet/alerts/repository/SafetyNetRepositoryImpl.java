package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.SafetyNetData;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

/**
 * Implementation of SafetyNetRepository that loads data from a JSON file.
 */
@Getter
@Repository
@Slf4j
public class SafetyNetRepositoryImpl implements SafetyNetRepository {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;


    @PostConstruct
    public void loadData() {
        log.info("Loading data from JSON file");
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");

            if (inputStream == null) {
                log.error("data.json not found in resources");
                throw new IllegalStateException("data.json not found in resources");
            }

            ObjectMapper mapper = new ObjectMapper();

            SafetyNetData data = mapper.readValue(inputStream, SafetyNetData.class);
            this.persons = data.getPersons();
            this.firestations = data.getFirestations();
            this.medicalRecords = data.getMedicalrecords();
            log.info("Data loaded successfully from JSON");
        } catch (Exception e) {

            log.error("Error loading data from JSON", e);
        }
    }
}


