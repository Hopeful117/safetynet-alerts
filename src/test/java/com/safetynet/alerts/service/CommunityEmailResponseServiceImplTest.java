package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Test class for CommunityEmailResponseServiceImpl.
 */
class CommunityEmailResponseServiceImplTest {

    private SafetyNetRepository repository;
    private CommunityEmailResponseServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new CommunityEmailResponseServiceImpl(repository);
    }

    /**
     * Test for getCommunityEmailResponse method to ensure distinct emails are returned for a given city.
     */
    @Test
    void getCommunityEmailResponse_shouldReturnDistinctEmails_forGivenCity() {
        // GIVEN
        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "addr1", "Culver", "97451", "111", "john@email.com"),
                new Person("Jacob", "Boyd", "addr2", "Culver", "97451", "222", "john@email.com"), // duplicate email
                new Person("Tenley", "Boyd", "addr3", "Culver", "97451", "333", "tenley@email.com"),
                new Person("Roger", "Boyd", "addr4", "OtherCity", "11111", "444", "roger@email.com")
        ));

        // WHEN
        CommunityEmailResponseDTO response =
                service.getCommunityEmailResponse("Culver");

        // THEN
        assertEquals(2, response.getEmails().size());
        assertTrue(response.getEmails().contains("john@email.com"));
        assertTrue(response.getEmails().contains("tenley@email.com"));
    }
    /**
     * Test for getCommunityEmailResponse method when no persons match the given city.
     */
    @Test
    void getCommunityEmailResponse_shouldReturnEmptyList_whenNoCityMatch() {
        // GIVEN
        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "addr1", "OtherCity", "11111", "111", "john@email.com")
        ));

        // WHEN
        CommunityEmailResponseDTO response =
                service.getCommunityEmailResponse("Culver");

        // THEN
        assertNotNull(response);
        assertTrue(response.getEmails().isEmpty());
    }
    /**
     * Test for getCommunityEmailResponse method to ensure city matching is case insensitive.
     */
    @Test
    void getCommunityEmailResponse_shouldBeCaseInsensitive() {
        // GIVEN
        when(repository.getPersons()).thenReturn(List.of(
                new Person("John", "Boyd", "addr1", "CULVER", "97451", "111", "john@email.com")
        ));

        // WHEN
        CommunityEmailResponseDTO response =
                service.getCommunityEmailResponse("culver");

        // THEN
        assertEquals(1, response.getEmails().size());
        assertEquals("john@email.com", response.getEmails().get(0));
    }
}
