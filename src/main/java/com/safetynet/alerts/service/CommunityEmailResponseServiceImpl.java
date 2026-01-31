package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.SafetyNetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
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
     * @return A CommunityEmailResponseDTO containing the list of email addresses.
     */
    @Override
    public CommunityEmailResponseDTO getCommunityEmailResponse(String city) {
        log.info("Recherche des emails pour la ville : {}", city);
        var emails = personRepository.getAll().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .toList();
        log.debug("Nombre d'emails trouvés pour la ville {}: {}", city, emails.size());
        return new CommunityEmailResponseDTO(emails);

    }
}