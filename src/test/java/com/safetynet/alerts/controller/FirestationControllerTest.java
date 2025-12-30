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
        FirestationRequestDTO request = new FirestationRequestDTO("123 New St",5);
        Firestation created = new Firestation("123 New St", 5);
        ObjectMapper objectMapper = new ObjectMapper();

        when(firestationService.addFirestationMapping( "123 New St",5)).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("123 New St"))
                .andExpect(jsonPath("$.station").value(5));
    }
    @Test
    public void testAddFirestationAlreadyExists() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO( "123 New St",5);
        ObjectMapper objectMapper = new ObjectMapper();
        when(firestationService.addFirestationMapping("123 New St",5))
                .thenThrow(new IllegalArgumentException("Cette adresse a déjà un mapping."));

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void updateFirestation_shouldReturn200_whenSuccess() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("1509 Culver St",3 );
        Firestation updated = new Firestation("1509 Culver St", 3);
        ObjectMapper objectMapper = new ObjectMapper();

        when(firestationService.updateFirestationMapping( "1509 Culver St",3)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value(3));
    }
    @Test
    void updateFirestation_shouldReturn400_whenAddressNotFound() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("Unknown", 2);
        ObjectMapper objectMapper = new ObjectMapper();
        when(firestationService.updateFirestationMapping( "Unknown",2))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void deleteFirestation_shouldReturn200_whenSuccess() throws Exception {
        doNothing().when(firestationService).deleteFirestationMapping("1509 Culver St");

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }
    @Test
    void deleteFirestation_shouldReturn400_whenAddressNotFound() throws Exception {
        doThrow(new IllegalArgumentException())
                .when(firestationService).deleteFirestationMapping("Unknown");

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .param("address", "Unknown"))
                .andExpect(status().isBadRequest());
    }



}
