package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for FireResponseServiceImpl.
 */
class FireResponseServiceImplTest {

    private FirestationRepository firestationRepository;
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private FireResponseService service;

    @BeforeEach
    void setUp() {
        firestationRepository = mock(FirestationRepository.class);
        personRepository = mock(PersonRepository.class);
        medicalRecordRepository = mock(MedicalRecordRepository.class);
        service = new FireResponseServiceImpl(firestationRepository, personRepository, medicalRecordRepository);
    }
/**
     * Test for getFireResponseByAddress method.
     */
    @Test
    void getFireResponseByAddress_shouldReturnResidentsAndStationNumber() {
        // GIVEN
        String address = "1509 Culver St";

        when(firestationRepository.findByAddress(address)).thenReturn(
                Optional.of(new Firestation(address, 3))
        );

        when(personRepository.getAllByAddress(address)).thenReturn(List.of(
                new Person("John", "Boyd", address, "Culver", "97451", "111-111", "john@email.com"),
                new Person("Tenley", "Boyd", address, "Culver", "97451", "222-222", "tenley@email.com")
        ));

        when(medicalRecordRepository.findByFirstAndLastName("John","Boyd")).thenReturn(
                Optional.of(new MedicalRecord(
                        "John",
                        "Boyd",
                        "03/06/1984",
                        List.of("med1"),
                        List.of("allergy1")
                )));
        when(medicalRecordRepository.findByFirstAndLastName("Tenley","Boyd")).thenReturn(
                Optional.of(
                new MedicalRecord(
                        "Tenley",
                        "Boyd",
                        "02/18/2012",
                        List.of(),
                        List.of("peanut")
                )
        ));

        // WHEN
        FireResponseDTO response = service.getFireResponseByAddress(address);

        // THEN
        assertNotNull(response);
        assertEquals(3, response.getStationNumber());
        assertNotNull(response.getResidents());




    }
}
