package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.CommunityEmailResponseDTO;
import com.safetynet.alerts.service.CommunityEmailResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for CommunityEmailResponseController.
 */
@WebMvcTest(CommunityEmailResponseController.class)
class CommunityEmailResponseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommunityEmailResponseService service;
/**
     * Test for getCommunityEmailResponse endpoint.
     * @throws Exception if an error occurs during the request
     */
    @Test
    void getCommunityEmailResponse_shouldReturnEmailsForCity() throws Exception {
        // GIVEN
        String city = "Culver";

        CommunityEmailResponseDTO responseDTO =
                new CommunityEmailResponseDTO(
                        List.of("john@email.com", "tenley@email.com")
                );

        when(service.getCommunityEmailResponse(city))
                .thenReturn(responseDTO);

        // WHEN + THEN
        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emails").isArray())
                .andExpect(jsonPath("$.emails.length()").value(2))
                .andExpect(jsonPath("$.emails[0]").value("john@email.com"))
                .andExpect(jsonPath("$.emails[1]").value("tenley@email.com"));
    }
}
