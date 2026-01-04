package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;

public interface CommunityEmailResponseService {
    public CommunityEmailResponseDTO getCommunityEmailResponse(String city);
}
