package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FloodResponseDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.FloodResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FloodController.class)
class FloodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FloodResponseService floodResponseService;

    @Test
    void flood_shouldReturnHouseholdsGroupedByAddress() throws Exception {
        // GIVEN
        List<Integer> stations = List.of(3);

        FloodResponseDTO responseDTO = new FloodResponseDTO(
                Map.of(
                        "1509 Culver St", List.of(
                                new ResidentsDTO(
                                        "John",
                                        "Boyd",
                                        "1509 Culver St",
                                        "111-111",
                                        40,
                                        List.of("med1"),
                                        List.of("allergy1")
                                ),
                                new ResidentsDTO(
                                        "Tenley",
                                        "Boyd",
                                        "1509 Culver St",
                                        "222-222",
                                        12,
                                        List.of(),
                                        List.of("peanut")
                                )
                        ),
                        "29 15th St", List.of(
                                new ResidentsDTO(
                                        "Peter",
                                        "Duncan",
                                        "29 15th St",
                                        "333-333",
                                        34,
                                        List.of("med2"),
                                        List.of()
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
