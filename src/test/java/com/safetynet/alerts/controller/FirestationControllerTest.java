package com.safetynet.alerts.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alerts.dto.FireStationPersonDTO;
import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.service.FirestationService;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testGetFirestationCoverage() throws Exception {

        FireStationPersonDTO p1 = new FireStationPersonDTO("John", "Boyd", "1509 Culver St", "841-874-6512");
        FireStationResponseDTO responseDTO = new FireStationResponseDTO(List.of(p1), 1, 0);

        when(firestationService.getFirestationCoverage(3)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                        .param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(1))
                .andExpect(jsonPath("$.childCount").value(0))
                .andExpect(jsonPath("$.persons[0].firstName").value("John"));
    }
}
