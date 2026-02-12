package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;
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
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for FloodResponseServiceImpl.
 */
class FloodResponseServiceImplTest {
    private FirestationRepository firestationRepository;
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
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
        Set<Integer> stations = Set.of(3, 4);

        when(firestationRepository.getAll()).thenReturn(List.of(
                new Firestation("1509 Culver St", 3),
                new Firestation("29 15th St", 3),
                new Firestation("834 Binoc Ave", 4),
                new Firestation("748 Townings Dr", 4)

        ));

        when(personRepository.getAllByAddress("1509 Culver St")).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "111", "a"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "222", "b")

        ));
        when(personRepository.getAllByAddress("29 15th St")).thenReturn(List.of(
                new Person("Peter", "Duncan", "29 15th St", "Culver", "97451", "333", "c")
        ));
        when(personRepository.getAllByAddress("834 Binoc Ave")).thenReturn(List.of(new Person("Foster", "Shepard", "834 Binoc Ave", "Culver", "97451", "444", "d")));
        when(personRepository.getAllByAddress("748 Townings Dr")).thenReturn(List.of(new Person("Reginold", "Walker", "748 Townings Dr", "Culver", "97451", "555", "e")));

        when(medicalRecordRepository.getAll()).thenReturn(List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984", List.of("med1"), List.of("allergy1")),
                new MedicalRecord("Tenley", "Boyd", "02/08/2012", List.of("med2"), List.of("allergy2")),
                new MedicalRecord("Peter", "Duncan", "09/09/1990", List.of("med3"), List.of("allergy3")),
                new MedicalRecord("Foster", "Shepard", "12/05/1985", List.of("med4"), List.of("allergy4")),
                new MedicalRecord("Reginold", "Walker", "10/10/1975", List.of("med5"), List.of("allergy5"))
        ));


        // WHEN
        FloodResponseDTO response = service.getFloodResponseByStationNumbers(stations);

        // THEN
        assertNotNull(response);

        Map<String, List<ResidentsDTO.Resident>> households = response.getHouseholds();


        assertTrue(households.containsKey("1509 Culver St"));
        assertTrue(households.containsKey("29 15th St"));
        assertTrue(households.containsKey("834 Binoc Ave"));
        assertTrue(households.containsKey("748 Townings Dr"));

        List<ResidentsDTO.Resident> culverResidents = households.get("1509 Culver St").stream().toList();
        assertEquals(2, culverResidents.size());


    }
}
