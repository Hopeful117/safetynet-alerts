package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryTest {
    @Mock
    SafetyNetRepository safetyNetRepository;
    MedicalRecordRepositoryImpl medicalRecordRepository;

    @BeforeEach
    void setUp() {
        medicalRecordRepository = new MedicalRecordRepositoryImpl(safetyNetRepository);
        when(safetyNetRepository.getMedicalRecords()).thenReturn(new ArrayList<>(List.of(
                new MedicalRecord("John", "Boyd", "03/06/1984", List.of("aznol:350mg", "hydrapermazol:100mg"), List.of("nillacilan")),
                new MedicalRecord("Jacob", "Boyd", "03/06/1989", List.of(), List.of())
        )));
    }

    @Test
    void getAll_ShouldReturnAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getAll();
        assert medicalRecords.size() == 2;
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnMedicalRecord() {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstAndLastName("John", "Boyd").orElse(null);
        assert medicalRecord != null;
        assert medicalRecord.getFirstName().equals("John");
        assert medicalRecord.getLastName().equals("Boyd");
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnEmpty() {
        assert medicalRecordRepository.findByFirstAndLastName("Jane", "Doe").isEmpty();
    }

    @Test
    void save_ShouldAddMedicalRecord() {
        MedicalRecord newRecord = new MedicalRecord("Jane", "Doe", "01/01/1990", List.of(), List.of());
        medicalRecordRepository.save(newRecord);
        assert medicalRecordRepository.getAll().size() == 3;
    }

    @Test
    void delete_ShouldRemoveMedicalRecord() {
        MedicalRecord medicalRecord = medicalRecordRepository.findByFirstAndLastName("John", "Boyd").orElse(null);
        assert medicalRecord != null;
        medicalRecordRepository.delete(medicalRecord);
        assert medicalRecordRepository.getAll().size() == 1;
    }


}
