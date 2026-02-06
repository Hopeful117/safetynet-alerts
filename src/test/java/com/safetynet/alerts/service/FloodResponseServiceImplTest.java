package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for FloodResponseServiceImpl.
 */
class FloodResponseServiceImplTest {
    private  FirestationRepository firestationRepository;
    private  PersonRepository personRepository;
    private  MedicalRecordRepository medicalRecordRepository;
    private FloodResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        firestationRepository = mock(FirestationRepository.class);
        personRepository = mock(PersonRepository.class);
        medicalRecordRepository = mock(MedicalRecordRepository.class);
        service = new FloodResponseServiceImpl(firestationRepository, personRepository, medicalRecordRepository);
    }

    /**
     * Test for getFloodResponseByStationNumbers method.
     */
    @Test
    void getFloodResponseByStationNumbers_shouldReturnHouseholdsGroupedByAddress() {
        // GIVEN
        int station = 3;

        when(firestationRepository.getAll()).thenReturn(List.of(
                new Firestation("1509 Culver St", 3),
                new Firestation("29 15th St", 3)
        ));

        when(personRepository.getAllByAddress("1509 Culver St")).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "111", "a"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "222", "b")

        ));
        when(personRepository.getAllByAddress("29 15th St")).thenReturn(List.of(
                new Person("Peter", "Duncan", "29 15th St", "Culver", "97451", "333", "c")
        ));

        when (medicalRecordRepository.getAll()).thenReturn(List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984", List.of("med1"), List.of("allergy1")),
                new MedicalRecord("Tenley", "Boyd", "02/08/2012", List.of("med2"), List.of("allergy2")),
                new MedicalRecord("Peter", "Duncan", "09/09/1990", List.of("med3"), List.of("allergy3"))
        ));


        // WHEN
        FloodResponseDTO response =
                service.getFloodResponseByStationNumbers(station);

        // THEN
        assertNotNull(response);

        Map<String, List<ResidentsDTO.Resident>> households = response.getHouseholds();


        assertTrue(households.containsKey("1509 Culver St"));
        assertTrue(households.containsKey("29 15th St"));

        List<ResidentsDTO.Resident> culverResidents = households.get("1509 Culver St").stream().toList();
        assertEquals(2, culverResidents.size());


    }
}
