package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for retrieving community email responses.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CommunityEmailResponseServiceImpl implements CommunityEmailResponseService {
    private final PersonRepository personRepository;


/**
     * Retrieves a list of unique email addresses for all residents in the specified city.
     *
     * @param city The name of the city.
     * @return A set of unique email addresses for residents in the specified city.
     */
    @Override
    public Set<String> getCommunityEmailResponse(String city) {
        log.info("Recherche des emails pour la ville : {}", city);

        return personRepository.getAll().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .collect(Collectors.toSet());

    }
}