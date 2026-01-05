package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for FloodResponseServiceImpl.
 */
class FloodResponseServiceImplTest {

    private SafetyNetRepository repository;
    private FloodResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new FloodResponseServiceImpl(repository);
    }

    /**
     * Test for getFloodResponseByStationNumbers method.
     */
    @Test
    void getFloodResponseByStationNumbers_shouldReturnHouseholdsGroupedByAddress() {
        // GIVEN
        List<Integer> stations = List.of(3);

        when(repository.getFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", 3),
                new Firestation("29 15th St", 3)
        ));

        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "111", "a"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "222", "b"),
                new Person("Peter", "Duncan", "29 15th St", "Culver", "97451", "333", "c")
        ));

        when(repository.getMedicalRecords()).thenReturn(List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984",
                        List.of("med1"), List.of("allergy1")),
                new MedicalRecord("Tenley", "Boyd", "02/18/2012",
                        List.of(), List.of("peanut")),
                new MedicalRecord("Peter", "Duncan", "01/01/1990",
                        List.of("med2"), List.of())
        ));

        // WHEN
        FloodResponseDTO response =
                service.getFloodResponseByStationNumbers(stations);

        // THEN
        assertNotNull(response);

        Map<String, List<ResidentsDTO>> households = response.getHouseholds();
        assertEquals(2, households.size());

        assertTrue(households.containsKey("1509 Culver St"));
        assertTrue(households.containsKey("29 15th St"));

        List<ResidentsDTO> culverResidents = households.get("1509 Culver St");
        assertEquals(2, culverResidents.size());

        ResidentsDTO john = culverResidents.get(0);
        assertEquals("John", john.getFirstName());
        assertEquals("Boyd", john.getLastName());
        assertNotNull(john.getAge());
        assertTrue(john.getAge() > 30);

        ResidentsDTO tenley = culverResidents.get(1);
        assertEquals("Tenley", tenley.getFirstName());
        assertTrue(tenley.getAge() < 18);

        List<ResidentsDTO> fifteenthResidents = households.get("29 15th St");
        assertEquals(1, fifteenthResidents.size());
        assertEquals("Peter", fifteenthResidents.get(0).getFirstName());
    }
}
