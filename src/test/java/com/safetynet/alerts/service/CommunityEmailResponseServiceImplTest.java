package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for CommunityEmailResponseServiceImpl.
 */
class CommunityEmailResponseServiceImplTest {

    private PersonRepository personRepository;
    private CommunityEmailResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        service = new CommunityEmailResponseServiceImpl(personRepository);
    }

    /**
     * Test for getCommunityEmailResponse method to ensure distinct emails are returned for a given city.
     */
    @Test
    void getCommunityEmailResponse_shouldReturnDistinctEmails_forGivenCity() {
        // GIVEN
        when(personRepository.getAll()).thenReturn(List.of(
                new Person("John", "Boyd", "addr1", "Culver", "97451", "111", "john@email.com"),
                new Person("Jacob", "Boyd", "addr2", "Culver", "97451", "222", "john@email.com"), // duplicate email
                new Person("Tenley", "Boyd", "addr3", "Culver", "97451", "333", "tenley@email.com"),
                new Person("Roger", "Boyd", "addr4", "OtherCity", "11111", "444", "roger@email.com")
        ));

        // WHEN
        Set<String> response =
                service.getCommunityEmailResponse("Culver");

        // THEN
        assertEquals(2, response.size());
        assertTrue(response.contains("john@email.com"));
        assertTrue(response.contains("tenley@email.com"));
    }

    /**
     * Test for getCommunityEmailResponse method when no persons match the given city.
     */
    @Test
    void getCommunityEmailResponse_shouldReturnEmptyList_whenNoCityMatch() {
        // GIVEN
        when(personRepository.getAll()).thenReturn(List.of(
                new Person("John", "Boyd", "addr1", "OtherCity", "11111", "111", "john@email.com")
        ));

        // WHEN
        Set<String> response =
                service.getCommunityEmailResponse("Culver");

        // THEN
        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    /**
     * Test for getCommunityEmailResponse method to ensure city matching is case insensitive.
     */
    @Test
    void getCommunityEmailResponse_shouldBeCaseInsensitive() {
        // GIVEN
        when(personRepository.getAll()).thenReturn(List.of(
                new Person("John", "Boyd", "addr1", "CULVER", "97451", "111", "john@email.com")
        ));

        // WHEN
        Set<String> response =
                service.getCommunityEmailResponse("culver");

        // THEN
        assertEquals(1, response.size());
        assertTrue(response.contains("john@email.com"));
    }
}
