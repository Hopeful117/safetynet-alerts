package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for FirestationServiceImpl.
 */
class FirestationServiceImplTest {

    private FirestationRepository firestationRepository;
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private FirestationServiceImpl service;

    @BeforeEach
    void setUp() {
        firestationRepository = Mockito.mock(FirestationRepository.class);
        personRepository = Mockito.mock(PersonRepository.class);
        medicalRecordRepository = Mockito.mock(MedicalRecordRepository.class);
        service = new FirestationServiceImpl(firestationRepository, personRepository, medicalRecordRepository);
    }

    /**
     * Test for getFirestationCoverage method.
     */
    @Test
    void testGetFirestationCoverage() {

        // 1️⃣ Données mock
        List<Firestation> firestations = List.of(
                new Firestation("1509 Culver St", 3)
        );

        List<Person> persons = Arrays.asList(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6513", "drk@email.com"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com")
        );

        List<MedicalRecord> medicalRecords = Arrays.asList(
                new MedicalRecord("John", "Boyd", "03/06/1984", null, null),
                new MedicalRecord("Jacob", "Boyd", "03/06/2020", null, null),
                new MedicalRecord("Tenley", "Boyd", "02/18/2022", null, null)
        );

        when(firestationRepository.getAllByStationNumber(anyInt())).thenReturn(firestations);
        when(personRepository.getAll()).thenReturn(persons);
        when(medicalRecordRepository.findByFirstAndLastName("John", "Boyd")).thenReturn(Optional.ofNullable(medicalRecords.get(0)));
        when(medicalRecordRepository.findByFirstAndLastName("Jacob", "Boyd")).thenReturn(Optional.ofNullable(medicalRecords.get(1)));
        when(medicalRecordRepository.findByFirstAndLastName("Tenley", "Boyd")).thenReturn(Optional.ofNullable(medicalRecords.get(2)));

        // 2️⃣ Appel du service
        FireStationResponseDTO response = service.getFirestationCoverage(3);

        // 3️⃣ Vérifications
        assertEquals(1, response.getAdultCount());  // John 1984 → adulte
        assertEquals(2, response.getChildCount());  // Jacob et Tenley → enfants
        assertEquals(3, response.getPersons().size()); // 3 personnes
    }

    /**
     * Test for addFirestationMapping method.
     */
    @Test
    void testAddFirestationMappingSuccess() {

        when(firestationRepository.findByAddress(anyString())).thenReturn(Optional.empty());

        boolean result = service.addFirestationMapping("123 New St", 5);

        assertTrue(result);
    }

    /**
     * Test for addFirestationMapping method when mapping already exists.
     */
    @Test
    void testAddFirestationMappingAlreadyExists() {
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("123 New St", 3));
        when(firestationRepository.findByAddress(anyString())).thenReturn(Optional.ofNullable(firestations.getFirst()));
        boolean result = service.addFirestationMapping("123 New St", 5);
        assertFalse(result);
    }


    /**
     * Test for updateFirestationMapping method.
     */
    @Test
    void updateFirestationMapping_shouldUpdateStation_whenAddressExists() {
        // GIVEN
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("1509 Culver St", 2));
        when(firestationRepository.findByAddress(anyString())).thenReturn(Optional.ofNullable(firestations.getFirst()));

        // WHEN
        boolean updated = service.updateFirestationMapping("1509 Culver St", 3);

        // THEN
        assertTrue(updated);
    }

    /**
     * Test for updateFirestationMapping method when address does not exist.
     */
    @Test
    void updateFirestationMapping_shouldReturnFalse_whenAddressDoesntExists() {
        when(firestationRepository.findByAddress(anyString())).thenReturn(Optional.empty());
        boolean updated = service.updateFirestationMapping("Unknown Address", 3);
        assertFalse(updated);
    }

    /**
     * Test for deleteFirestationMapping method.
     */
    @Test
    void deleteFirestationMapping_shouldRemoveMapping_whenAddressExists() {
        // GIVEN
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("1509 Culver St", 2));
        when(firestationRepository.findByAddress(anyString())).thenReturn(firestations.stream().findFirst());

        // WHEN
        boolean result = service.deleteFirestationMapping("1509 Culver St");

        // THEN
        assertTrue(result);
    }

    /**
     * Test for deleteFirestationMapping method when address does not exist.
     */
    @Test
    void deleteFirestationMapping_shouldReturnFalse_whenAddressNotFound() {
        when(firestationRepository.findByAddress(anyString())).thenReturn(Optional.empty());

        boolean result = service.deleteFirestationMapping("Unknown Address");

        assertFalse(result);
    }

}
