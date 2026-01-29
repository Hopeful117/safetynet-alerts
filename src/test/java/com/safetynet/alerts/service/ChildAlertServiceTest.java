package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.PersonRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Test class for ChildAlertService.
 */
class ChildAlertServiceTest {
    private PersonRepository personRepository;
    private MedicalRecordRepository medicalRecordRepository;
    private ChildAlertService service;
    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepositoryImpl.class);
        service = new ChildAlertServiceImpl(personRepository,medicalRecordRepository);
    }
    /**
     * Test for getChildAlertByAddress method.
     */
    @Test
    void getChildAlertByAddress_shouldReturnChildrenAndAdults() {
        // GIVEN
        when(personRepository.getAll()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "123", "john@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "123", "jacob@email.com"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "123", "tenley@email.com")
        ));

        when(medicalRecordRepository.getAll()).thenReturn(List.of(
                new MedicalRecord("John", "Boyd", "01/01/1984", List.of(), List.of()),
                new MedicalRecord("Jacob", "Boyd", "01/01/1989", List.of(), List.of()),
                new MedicalRecord("Tenley", "Boyd", "01/01/2015", List.of(), List.of())
        ));

        // WHEN
        final ChildAlertResponseDTO response =
                service.getChildAlertByAddress("1509 Culver St");

        // THEN
        assertEquals(1, response.getChildren().size());
        assertEquals(2, response.getAdults().size());
    }
    /**
     * Test for getChildAlertByAddress method when no child is present.
     */
    @Test
    void getChildAlertByAddress_shouldReturnEmptyChildren_whenNoChild() {
        ChildAlertResponseDTO response =
                service.getChildAlertByAddress("Adult Only Street");

        assertTrue(response.getChildren().isEmpty());
    }

}
