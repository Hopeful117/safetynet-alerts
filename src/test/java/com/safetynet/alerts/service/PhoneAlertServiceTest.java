package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PhoneAlertResponseDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Test class for PhoneAlertServiceImpl.
 */
class PhoneAlertServiceTest {
    private SafetyNetRepository repository;
    private PhoneAlertServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new PhoneAlertServiceImpl(repository);
    }
    /**
     * Test for getPhoneAlertByStationNumber method.
     */
    @Test
    void getPhoneAlertByStation_shouldReturnDistinctPhones() {

        when(repository.getFirestations()).thenReturn(List.of(
                new Firestation("1509 Culver St", 3),
                new Firestation("29 15th St", 3)
        ));

        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "111", "a"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "111", "b"),
                new Person("Tenley", "Boyd", "29 15th St", "Culver", "97451", "222", "c")
        ));

        PhoneAlertResponseDTO response =
                service.getPhoneAlertByStationNumber(3);

        assertEquals(2, response.getPhones().size());
        assertTrue(response.getPhones().contains("111"));
        assertTrue(response.getPhones().contains("222"));
    }

}
