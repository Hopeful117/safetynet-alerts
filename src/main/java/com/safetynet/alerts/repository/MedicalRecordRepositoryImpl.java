package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository{
    final SafetyNetRepository safetyNetRepository;

    @Override
    public List<MedicalRecord> getAll() {
        return safetyNetRepository.getMedicalRecords();
    }

    @Override
    public Optional<MedicalRecord> findByFirstAndLastName(String firstname,String lastname) {
        return safetyNetRepository.getMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstname))
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastname))
                .findFirst();

    }
    @Override
    public void save(MedicalRecord medicalRecord){
        getAll().add(medicalRecord);
    }

    @Override
    public void delete(MedicalRecord medicalRecord){
        getAll().remove(medicalRecord);
    }
}
