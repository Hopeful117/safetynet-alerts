package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FirestationRepositoryTest {

    @Mock
    SafetyNetRepository safetyNetRepository;
    FirestationRepositoryImpl firestationRepository;

    @BeforeEach
    void setUp() {
       firestationRepository = new FirestationRepositoryImpl(safetyNetRepository);
       when(safetyNetRepository.getFirestations()).thenReturn(new ArrayList<>(List.of(
               new Firestation("1509 Culver St", 3),
               new Firestation("29 15th St", 1)
       )));
     }
        @Test
    void getALl_should_return_all_firestations() {
        // Given

        // When
        var result = firestationRepository.getAll();

        // Then
        assert result.size() == 2;
    }

     @Test
    void getAllByStationNumber_should_return_firestations_with_given_station_number() {
        // Given

        // When
        var result = firestationRepository.getAllByStationNumber(1);

        // Then
        assert result.size() == 1;
     }

     @Test
    void getAllByStationNumber_should_return_empty_list_if_no_firestation_with_given_station_number() {
        // Given

        // When
        var result = firestationRepository.getAllByStationNumber(99);

        // Then
        assert result.isEmpty();
     }

     @Test
    void findByAddress_should_return_firestation_with_given_address() {
        // Given

        // When
        var result = firestationRepository.findByAddress("1509 Culver St");

        // Then
        assert result.isPresent();
     }
     @Test
    void findByAddress_should_return_empty_optional_if_no_firestation_with_given_address() {
        // Given

        // When
        var result = firestationRepository.findByAddress("unknown address");

        // Then
        assert result.isEmpty();
     }

     @Test
    void save_should_add_firestation_to_repository() {
        // Given
        var firestation = new Firestation("new address", 99);

        // When
        firestationRepository.save(firestation);

        // Then
        assert firestationRepository.getAll().contains(firestation);
     }

     @Test
    void delete_should_remove_firestation_from_repository() {
        // Given
        var firestation = new Firestation("1509 Culver St", 3);

        // When
        firestationRepository.delete(firestation);

        // Then
        assert !firestationRepository.getAll().contains(firestation);
     }



}
