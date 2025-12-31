package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChildAlertServiceTest {
    private SafetyNetRepository repository;
    private ChildAlertService service;
    @BeforeEach
    public void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new ChildAlertServiceImpl(repository);
    }
    @Test
    void getChildAlertByAddress_shouldReturnChildrenAndAdults() {
        // GIVEN
        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "123", "john@email.com"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "123", "jacob@email.com"),
                new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "123", "tenley@email.com")
        ));

        when(repository.getMedicalRecords()).thenReturn(List.of(
                new MedicalRecord("John", "Boyd", "01/01/1984", List.of(), List.of()),
                new MedicalRecord("Jacob", "Boyd", "01/01/1989", List.of(), List.of()),
                new MedicalRecord("Tenley", "Boyd", "01/01/2015", List.of(), List.of())
        ));
        ChildAlertResponseDTO response =
                service.getChildAlertByAddress("1509 Culver St");

        assertEquals(1, response.getChildren().size());
        assertEquals(2, response.getAdults().size());
    }
    @Test
    void getChildAlertByAddress_shouldReturnEmptyChildren_whenNoChild() {
        ChildAlertResponseDTO response =
                service.getChildAlertByAddress("Adult Only Street");

        assertTrue(response.getChildren().isEmpty());
    }

}
