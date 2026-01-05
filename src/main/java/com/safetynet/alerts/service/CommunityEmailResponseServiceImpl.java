package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.repository.SafetyNetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
/**
 * Service implementation for retrieving community email responses.
 */
@Service
public class CommunityEmailResponseServiceImpl implements CommunityEmailResponseService {
    private static final Logger LOGGER = LogManager.getLogger(CommunityEmailResponseServiceImpl.class);
    private final SafetyNetRepository repository;

    public CommunityEmailResponseServiceImpl(SafetyNetRepository repository) {
        this.repository = repository;
    }
/**
     * Retrieves a list of unique email addresses for all residents in the specified city.
     *
     * @param city The name of the city.
     * @return A CommunityEmailResponseDTO containing the list of email addresses.
     */
    @Override
    public CommunityEmailResponseDTO getCommunityEmailResponse(String city) {
        LOGGER.info("Recherche des emails pour la ville : {}", city);
        var emails = repository.getPersons().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(p -> p.getEmail())
                .distinct()
                .toList();
        LOGGER.debug("Nombre d'emails trouvés pour la ville {}: {}", city, emails.size());
        return new CommunityEmailResponseDTO(emails);

    }
}