package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Test class for PhoneAlertServiceImpl.
 */
class PhoneAlertServiceTest {
    private  FirestationRepository firestationRepository;
    private  PersonRepository personRepository;
    private PhoneAlertServiceImpl service;

    @BeforeEach
    void setUp() {
        firestationRepository = mock(FirestationRepository.class);
        personRepository = mock(PersonRepository.class);
        service = new PhoneAlertServiceImpl(firestationRepository, personRepository);
    }
    /**
     * Test for getPhoneAlertByStationNumber method.
     */
    @Test
    void getPhoneAlertByStation_shouldReturnDistinctPhones() {

        when(firestationRepository.getAllByStationNumber(anyInt())).thenReturn(List.of(
                new Firestation("1509 Culver St", 3),
                new Firestation("29 15th St", 3)
        ));

        when(personRepository.getAll()).thenReturn(List.of(
                new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "111", "a"),
                new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "111", "b"),
                new Person("Tenley", "Boyd", "29 15th St", "Culver", "97451", "222", "c")
        ));

       Set<String> response =
                service.getPhoneAlertByStationNumber(3);

        assertEquals(2, response.size());
        assertTrue(response.contains("111"));
        assertTrue(response.contains("222"));
    }

}
