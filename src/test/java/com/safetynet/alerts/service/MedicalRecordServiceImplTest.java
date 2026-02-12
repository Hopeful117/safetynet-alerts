package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for MedicalRecordServiceImpl.
 */
class MedicalRecordServiceImplTest {


    private MedicalRecordRepository medicalRecordRepository;
    private MedicalRecordService service;


    @BeforeEach
    void setUp() {
        medicalRecordRepository = mock(MedicalRecordRepository.class);
        service = new MedicalRecordServiceImpl(medicalRecordRepository);
    }

    /**
     * Test for addMedicalRecord method.
     */
    @Test
    void addMedicalRecord_shouldAddAndReturnMedicalRecord() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        when(medicalRecordRepository.findByFirstAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        MedicalRecordDTO dto = new MedicalRecordDTO(
                "John",
                "Doe",
                "01/01/1990",
                List.of("med1"),
                List.of("allergy1")
        );

        boolean result = service.addMedicalRecord(dto);

        assertTrue(result);


    }

    /**
     * Test for updateMedicalRecord method.
     */
    @Test
    void updateMedicalRecord_shouldUpdateAndReturnMedicalRecord() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord existing = new MedicalRecord(
                "John",
                "Doe",
                "01/01/1990",
                List.of("oldMed"),
                List.of()
        );
        medicalRecords.add(existing);
        when(medicalRecordRepository.findByFirstAndLastName(anyString(), anyString())).thenReturn(medicalRecords.stream().findFirst());


        MedicalRecordDTO dto = new MedicalRecordDTO(
                "John",
                "Doe",
                "02/02/1995",
                List.of("newMed"),
                List.of("peanut")
        );

        boolean updated = service.updateMedicalRecord(dto);

        assertTrue(updated);

    }

    /**
     * Test for updateMedicalRecord method when record not found.
     */
    @Test
    void updateMedicalRecord_shouldReturnNull_whenNotFound() {

        when(medicalRecordRepository.findByFirstAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        MedicalRecordDTO dto = new MedicalRecordDTO(
                "Jane",
                "Doe",
                "01/01/1990",
                List.of(),
                List.of()
        );

        boolean result = service.updateMedicalRecord(dto);

        assertFalse(result);
    }

    /**
     * Test for deleteMedicalRecord method.
     */
    @Test
    void deleteMedicalRecord_shouldReturnTrue_whenDeleted() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        MedicalRecord existing = new MedicalRecord(
                "John",
                "Doe",
                "01/01/1990",
                List.of(),
                List.of()
        );
        medicalRecords.add(existing);
        when(medicalRecordRepository.findByFirstAndLastName(anyString(), anyString())).thenReturn(medicalRecords.stream().findFirst());


        boolean deleted = service.deleteMedicalRecord("John", "Doe");

        assertTrue(deleted);

    }

    /**
     * Test for deleteMedicalRecord method when record not found.
     */
    @Test
    void deleteMedicalRecord_shouldReturnFalse_whenNotFound() {
        when(medicalRecordRepository.findByFirstAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
        boolean deleted = service.deleteMedicalRecord("Jane", "Doe");

        assertFalse(deleted);
    }


}
