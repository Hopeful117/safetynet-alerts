package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 * Test class for PersonInfolastNameServiceImpl.
 */
class PersonInfolastNameServiceImplTest {

    private SafetyNetRepository repository;
    private PersonInfolastNameServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(SafetyNetRepository.class);
        service = new PersonInfolastNameServiceImpl(repository);
    }
    /**
     * Test for getPersonInfoByLastName method.
     */
    @Test
    void getPersonInfoByLastName_shouldReturnResidents_whenLastNameExists() {
        // GIVEN
        when(repository.getPersons()).thenReturn(List.of(
                new Person(
                        "John", "Boyd", "1509 Culver St",
                        "Culver", "97451", "111-111", "john@email.com"
                ),
                new Person(
                        "Tenley", "Boyd", "1509 Culver St",
                        "Culver", "97451", "222-222", "tenley@email.com"
                )
        ));

        when(repository.getMedicalRecords()).thenReturn(List.of(
                new MedicalRecord(
                        "John", "Boyd", "01/01/1984",
                        List.of("med1"), List.of("peanut")
                ),
                new MedicalRecord(
                        "Tenley", "Boyd", "01/01/2012",
                        List.of(), List.of("dust")
                )
        ));

        // WHEN
        PersonInfolastNameDTO result = service.getPersonInfoByLastName("Boyd");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getResidents()).hasSize(2);

        assertThat(result.getResidents().get(0).getLastName()).isEqualTo("Boyd");
        assertThat(result.getResidents().get(1).getLastName()).isEqualTo("Boyd");
    }
    /**
     * Test for getPersonInfoByLastName method when no persons match the last name.
     */
    @Test
    void getPersonInfoByLastName_shouldReturnEmptyList_whenNoMatch() {
        // GIVEN
        when(repository.getPersons()).thenReturn(List.of());
        when(repository.getMedicalRecords()).thenReturn(List.of());

        // WHEN
        PersonInfolastNameDTO result = service.getPersonInfoByLastName("Unknown");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getResidents()).isEmpty();
    }
}
