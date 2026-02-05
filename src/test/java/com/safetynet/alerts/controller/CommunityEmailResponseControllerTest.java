package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.CommunityEmailResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;

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

    @MockitoBean
    private CommunityEmailResponseService service;

    @Autowired
    private ObjectMapper objectMapper;
/**
     * Test for getCommunityEmailResponse endpoint.
     * @throws Exception if an error occurs during the request
     */
    @Test
    void getCommunityEmailResponse_shouldReturnEmailsForCity() throws Exception {
        // GIVEN
        String city = "Culver";

        Set<String> response = Set.of("john@email.com", "tenley@email.com");



        when(service.getCommunityEmailResponse(city))
                .thenReturn(response);

        // WHEN + THEN
        mockMvc.perform(get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

    }
}
