package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.FloodResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for FloodController.
 */
@WebMvcTest(FloodController.class)
class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FloodResponseService floodResponseService;

    /**
     * Test for flood endpoint.
     * @throws Exception
     */
    @Test
    void flood_shouldReturnHouseholdsGroupedByAddress() throws Exception {
        // GIVEN
        int stations = 3;

        FloodResponseDTO responseDTO = new FloodResponseDTO(
                Map.of(
                        "1509 Culver St",

                               (
                                        List.of(
                                                new ResidentsDTO.Resident(
                                                        "John", "Boyd", "1509 Culver St",
                                                        "841-874-6512", 40,
                                                        List.of("med1:100mg"),
                                                        List.of("allergy1")
                                                ),
                                                new ResidentsDTO.Resident(
                                                        "Jane", "Doe", "1509 Culver St",
                                                        "841-874-6513", 35,
                                                        List.of("med2:200mg"),
                                                        List.of("allergy2")
                                                )
                                        )
                                )
                        ,

                        "29 15th St",

                                (
                                        List.of(
                                                new ResidentsDTO.Resident(
                                                        "Peter", "Smith", "29 15th St",
                                                        "841-874-6514", 25,
                                                        List.of("med3:300mg"),
                                                        List.of("allergy3")
                                                )
                                        )
                                )

                )
        );

        when(floodResponseService.getFloodResponseByStationNumbers(eq(stations)))
                .thenReturn(responseDTO);

        // WHEN + THEN
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.households").exists())
                .andExpect(jsonPath("$.households['1509 Culver St']").isArray())
                .andExpect(jsonPath("$.households['1509 Culver St'].length()").value(2))
                .andExpect(jsonPath("$.households['1509 Culver St'][0].firstName").value("John"))
                .andExpect(jsonPath("$.households['1509 Culver St'][0].age").value(40))
                .andExpect(jsonPath("$.households['29 15th St']").isArray())
                .andExpect(jsonPath("$.households['29 15th St'].length()").value(1))
                .andExpect(jsonPath("$.households['29 15th St'][0].firstName").value("Peter"));
    }
}
