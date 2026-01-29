package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository{
    final SafetyNetRepository safetyNetRepository;

    @Override
    public List<MedicalRecord> getAll() {
        return safetyNetRepository.getMedicalRecords();
    }
}
