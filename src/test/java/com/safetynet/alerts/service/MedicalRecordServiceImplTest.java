package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MedicalRecordServiceImplTest {


    private SafetyNetRepository repository;
    private MedicalRecordService service;


    @BeforeEach
    void setUp() {
       repository=mock(SafetyNetRepository.class);
       service=new MedicalRecordServiceImpl(repository);
    }
    @Test
    void addMedicalRecord_shouldAddAndReturnMedicalRecord() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        when(repository.getMedicalRecords()).thenReturn(medicalRecords);
        MedicalRecordDTO dto = new MedicalRecordDTO(
                "John",
                "Doe",
                "01/01/1990",
                List.of("med1"),
                List.of("allergy1")
        );

        MedicalRecord result = service.addMedicalRecord(dto);

        assertNotNull(result);
        assertEquals(1, medicalRecords.size());
        assertEquals("John", medicalRecords.get(0).getFirstName());
        assertEquals("Doe", medicalRecords.get(0).getLastName());
    }
    @Test
    void updateMedicalRecord_shouldUpdateAndReturnMedicalRecord() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        when(repository.getMedicalRecords()).thenReturn(medicalRecords);
        MedicalRecord existing = new MedicalRecord(
                "John",
                "Doe",
                "01/01/1990",
                List.of("oldMed"),
                List.of()
        );
        medicalRecords.add(existing);

        MedicalRecordDTO dto = new MedicalRecordDTO(
                "John",
                "Doe",
                "02/02/1995",
                List.of("newMed"),
                List.of("peanut")
        );

        MedicalRecord updated = service.updateMedicalRecord(dto);

        assertNotNull(updated);
        assertEquals("02/02/1995", updated.getBirthdate());
        assertEquals(List.of("newMed"), updated.getMedications());
        assertEquals(List.of("peanut"), updated.getAllergies());
    }
    @Test
    void updateMedicalRecord_shouldReturnNull_whenNotFound() {
        MedicalRecordDTO dto = new MedicalRecordDTO(
                "Jane",
                "Doe",
                "01/01/1990",
                List.of(),
                List.of()
        );

        MedicalRecord result = service.updateMedicalRecord(dto);

        assertNull(result);
    }
    @Test
    void deleteMedicalRecord_shouldReturnTrue_whenDeleted() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        when(repository.getMedicalRecords()).thenReturn(medicalRecords);
        MedicalRecord existing = new MedicalRecord(
                "John",
                "Doe",
                "01/01/1990",
                List.of(),
                List.of()
        );
        medicalRecords.add(existing);

        boolean deleted = service.deleteMedicalRecord("John", "Doe");

        assertTrue(deleted);
        assertTrue(medicalRecords.isEmpty());
    }
    @Test
    void deleteMedicalRecord_shouldReturnFalse_whenNotFound() {
        boolean deleted = service.deleteMedicalRecord("Jane", "Doe");

        assertFalse(deleted);
    }





}
