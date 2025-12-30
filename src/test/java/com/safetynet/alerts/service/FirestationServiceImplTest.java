package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FirestationServiceImplTest {

    private SafetyNetRepository repository;
    private FirestationServiceImpl service;

    @BeforeEach
    public void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new FirestationServiceImpl(repository);
    }

    @Test
    public void testGetFirestationCoverage() {

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
                new MedicalRecord("Jacob", "Boyd", "03/06/2010", null, null),
                new MedicalRecord("Tenley", "Boyd", "02/18/2012", null, null)
        );

        when(repository.getFirestations()).thenReturn(firestations);
        when(repository.getPersons()).thenReturn(persons);
        when(repository.getMedicalRecords()).thenReturn(medicalRecords);

        // 2️⃣ Appel du service
        FireStationResponseDTO response = service.getFirestationCoverage(3);

        // 3️⃣ Vérifications
        assertEquals(1, response.getAdultCount());  // John 1984 → adulte
        assertEquals(2, response.getChildCount());  // Jacob et Tenley → enfants
        assertEquals(3, response.getPersons().size()); // 3 personnes
    }
    @Test
    public void testAddFirestationMappingSuccess() {
        List<Firestation> firestations = new ArrayList<>();
        when(repository.getFirestations()).thenReturn(firestations);

        Firestation result = service.addFirestationMapping("123 New St", 5);

        assertEquals("123 New St", result.getAddress());
        assertEquals(5, result.getStation());
        assertEquals(1, firestations.size());
    }

    @Test
    public void testAddFirestationMappingAlreadyExists() {
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("123 New St", 3));
        when(repository.getFirestations()).thenReturn(firestations);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addFirestationMapping( "123 New St", 5);
        });

        assertEquals("Cette adresse a déjà un mapping.", exception.getMessage());
    }
    @Test
    void updateFirestationMapping_shouldUpdateStation_whenAddressExists() {
        // GIVEN
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("1509 Culver St", 2));
        when(repository.getFirestations()).thenReturn(firestations);

        // WHEN
        Firestation updated = service.updateFirestationMapping( "1509 Culver St", 3);

        // THEN
        assertEquals(3, updated.getStation());
    }
    @Test
    void updateFirestationMapping_shouldThrowException_whenAddressNotFound() {
        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                service.updateFirestationMapping( "Unknown Address", 2)
        );
    }
    @Test
    void deleteFirestationMapping_shouldRemoveMapping_whenAddressExists() {
        // GIVEN
        List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("1509 Culver St", 2));
        when(repository.getFirestations()).thenReturn(firestations);

        // WHEN
        service.deleteFirestationMapping("1509 Culver St");

        // THEN
        assertTrue(repository.getFirestations().isEmpty());
    }
    @Test
    void deleteFirestationMapping_shouldThrowException_whenAddressNotFound() {
        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () ->
                service.deleteFirestationMapping("Unknown Address")
        );
    }

}
