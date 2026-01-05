package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Test class for FireResponseServiceImpl.
 */
class FireResponseServiceImplTest {

    private SafetyNetRepository repository;
    private FireResponseService service;

    @BeforeEach
    void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new FireResponseServiceImpl(repository);
    }
/**
     * Test for getFireResponseByAddress method.
     */
    @Test
    void getFireResponseByAddress_shouldReturnResidentsAndStationNumber() {
        // GIVEN
        String address = "1509 Culver St";

        when(repository.getFirestations()).thenReturn(List.of(
                new Firestation(address, 3)
        ));

        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", address, "Culver", "97451", "111-111", "john@email.com"),
                new Person("Tenley", "Boyd", address, "Culver", "97451", "222-222", "tenley@email.com")
        ));

        when(repository.getMedicalRecords()).thenReturn(List.of(
                new MedicalRecord(
                        "John",
                        "Boyd",
                        "03/06/1984",
                        List.of("med1"),
                        List.of("allergy1")
                ),
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

        List<ResidentsDTO> residents = response.getResidents();
        assertEquals(2, residents.size());

        ResidentsDTO john = residents.stream()
                .filter(r -> r.getFirstName().equals("John"))
                .findFirst()
                .orElseThrow();

        assertTrue(john.getAge() >= 18);
        assertEquals(List.of("med1"), john.getMedications());
        assertEquals(List.of("allergy1"), john.getAllergies());

        ResidentsDTO tenley = residents.stream()
                .filter(r -> r.getFirstName().equals("Tenley"))
                .findFirst()
                .orElseThrow();

        assertTrue(tenley.getAge() < 18);
        assertEquals(List.of("peanut"), tenley.getAllergies());
    }
}
