package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.PhoneAlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for PhoneAlertController.
 */
@WebMvcTest(PhoneAlertController.class)
public class PhoneAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PhoneAlertService phoneAlertService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        /** Setup before each test if necessary */
    }

    /**
     * Test for phoneAlert endpoint.
     *
     * @throws Exception
     */
    @Test
    void phoneAlert_shouldReturnDistinctPhones() throws Exception {
        // GIVEN
        Set<String> response = Set.of("111", "222");


        when(phoneAlertService.getPhoneAlertByStationNumber(3))
                .thenReturn(response);

        // WHEN / THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/phoneAlert")
                        .param("firestation", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}

