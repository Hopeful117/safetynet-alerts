package com.safetynet.alerts.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.safetynet.alerts.dto.FirestationRequestDTO;
import com.safetynet.alerts.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alerts.dto.FireStationPersonDTO;
import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.service.FirestationService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    @Test
    public void testAddFirestationSuccess() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO(5,"123 New St");
        Firestation created = new Firestation("123 New St", 5);
        ObjectMapper objectMapper = new ObjectMapper();

        when(firestationService.addFirestationMapping( 5,"123 New St")).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("123 New St"))
                .andExpect(jsonPath("$.station").value(5));
    }
    @Test
    public void testAddFirestationAlreadyExists() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO( 5,"123 New St");
        ObjectMapper objectMapper = new ObjectMapper();
        when(firestationService.addFirestationMapping(5,"123 New St"))
                .thenThrow(new IllegalArgumentException("Cette adresse a déjà un mapping."));

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
