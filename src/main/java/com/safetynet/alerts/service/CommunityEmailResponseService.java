package com.safetynet.alerts.service;

import java.util.Set;

/**
 * Service interface for retrieving community email responses based on city.
 */
public interface CommunityEmailResponseService {
    public Set<String> getCommunityEmailResponse(String city);
}
