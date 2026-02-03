package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirestationRepositoryImpl implements FirestationRepository {
    final SafetyNetRepository safetyNetRepository;

    @Override
    public List<Firestation> getAll() {return safetyNetRepository.getFirestations();}

    @Override
    public List<Firestation>getAllByStationNumber(int stationNumber){
        return safetyNetRepository.getFirestations().stream()
                .filter(firestation -> stationNumber == firestation.getStation())
                .toList();
    }
    @Override
    public Optional<Firestation> findByAddress(String address){
        return safetyNetRepository.getFirestations().stream()
                .filter(firestation -> address.equals(firestation.getAddress()))
                .findFirst();
    }

    @Override
    public void save (Firestation firestation){
        getAll().add(firestation);
    }

    @Override
    public void delete(Firestation firestation){
        getAll().remove(firestation);
    }

}
