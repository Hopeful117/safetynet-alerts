package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildAlertResponseDTO;
import com.safetynet.alerts.service.ChildAlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for ChildAlertController.
 */
@WebMvcTest(ChildAlertController.class)
public class ChildAlertControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChildAlertService service;

    /**
     * Test for getChildAlert endpoint.
     * @throws Exception if an error occurs during the request
     */
    @Test
    void getChildAlert_shouldReturn200() throws Exception {
        when(service.getChildAlertByAddress(any()))
                .thenReturn(new ChildAlertResponseDTO(List.of(), List.of()));

        mockMvc.perform(MockMvcRequestBuilders.get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }
}
