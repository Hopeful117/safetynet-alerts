package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
/**
 * Service interface for retrieving community email responses based on city.
 */
public interface CommunityEmailResponseService {
    public CommunityEmailResponseDTO getCommunityEmailResponse(String city);
}
