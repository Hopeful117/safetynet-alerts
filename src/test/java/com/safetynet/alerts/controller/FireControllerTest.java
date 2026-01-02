package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.FireResponseService;
import com.safetynet.alerts.service.FireResponseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireController.class)
class FireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireResponseService fireResponseService;

    @Test
    void fire_shouldReturnResidentsAndStationNumber() throws Exception {
        // GIVEN
        String address = "1509 Culver St";

        FireResponseDTO responseDTO = new FireResponseDTO(
                List.of(
                        new ResidentsDTO(
                                "John",
                                "Boyd",
                                address,
                                "111-111",
                                40,
                                List.of("med1"),
                                List.of("allergy1")
                        ),
                        new ResidentsDTO(
                                "Tenley",
                                "Boyd",
                                address,
                                "222-222",
                                12,
                                List.of(),
                                List.of("peanut")
                        )
                ),
                3
        );

        when(fireResponseService.getFireResponseByAddress(address))
                .thenReturn(responseDTO);

        // WHEN + THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/fire")
                        .param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumber").value(3))
                .andExpect(jsonPath("$.residents").isArray())
                .andExpect(jsonPath("$.residents.length()").value(2))
                .andExpect(jsonPath("$.residents[0].firstName").value("John"))
                .andExpect(jsonPath("$.residents[0].age").value(40))
                .andExpect(jsonPath("$.residents[1].firstName").value("Tenley"))
                .andExpect(jsonPath("$.residents[1].age").value(12));
    }
}
