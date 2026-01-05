package com.safetynet.alerts.repository;
import java.util.List;
import java.io.InputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import com.safetynet.alerts.model.SafetyNetData;
/**
 * Implementation of SafetyNetRepository that loads data from a JSON file.
 */
@Repository
public class SafetyNetRepositoryImpl implements SafetyNetRepository {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;
    private static final Logger LOGGER = LogManager.getLogger(SafetyNetRepositoryImpl.class);

    @PostConstruct
    public void loadData() {
        LOGGER.info("Loading data from JSON file");
        try {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.json");
        if (inputStream == null) {
            LOGGER.error("data.json not found in resources");
            throw new IllegalStateException("data.json not found in resources");
        }

        ObjectMapper mapper = new ObjectMapper();

            SafetyNetData data = mapper.readValue(inputStream, SafetyNetData.class);
            this.persons = data.getPersons();
            this.firestations = data.getFirestations();
            this.medicalRecords = data.getMedicalRecords();
        LOGGER.info("Data loaded successfully from JSON");
        } catch (Exception e) {

            LOGGER.error("Error loading data from JSON", e);
        }
    }

    @Override
    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public List<Firestation> getFirestations() {
        return firestations;
    }

    @Override
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}


