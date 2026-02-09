package com.safetynet.alerts.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.safetynet.alerts.dto.FirestationRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alerts.dto.FireStationResponseDTO;
import com.safetynet.alerts.service.FirestationService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
/**
 * Test class for FirestationController.
 */
@WebMvcTest(FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FirestationService firestationService;

    @BeforeEach
    void setUp() {
        /** Setup before each test if necessary */
    }

    /**
     * Test for getFirestationCoverage endpoint.
     * @throws Exception
     */
    @Test
    public void testGetFirestationCoverage() throws Exception {

        FireStationResponseDTO.FireStationPersonDTO p1 = new FireStationResponseDTO.FireStationPersonDTO("John", "Boyd", "1509 Culver St", "841-874-6512");
        FireStationResponseDTO responseDTO = new FireStationResponseDTO(List.of(p1), 1, 0);

        when(firestationService.getFirestationCoverage(3)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/firestation")
                        .param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(1))
                .andExpect(jsonPath("$.childCount").value(0))
                .andExpect(jsonPath("$.persons[0].firstName").value("John"));
    }
    /**
     * Test for addFirestation endpoint.
     * @throws Exception
     */
    @Test
    public void testAddFirestationSuccess() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("123 New St",5);
        ObjectMapper objectMapper = new ObjectMapper();

        when(firestationService.addFirestationMapping( "123 New St",5)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("123 New St"))
                .andExpect(jsonPath("$.station").value(5));
    }
    /**
     * Test for addFirestation endpoint when the firestation already exists.
     * @throws Exception
     */
    @Test
    public void testAddFirestationAlreadyExists() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO( "123 New St",5);
        ObjectMapper objectMapper = new ObjectMapper();
        when(firestationService.addFirestationMapping("123 New St",5))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
    /**
     * Test for updateFirestation endpoint.
     * @throws Exception
     */
    @Test
    public void testAddFirestationInvalidInput() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("", -1);
        ObjectMapper objectMapper = new ObjectMapper();
        when(firestationService.addFirestationMapping("", -1)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void updateFirestation_shouldReturn202_whenSuccess() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("1509 Culver St",3 );
        ObjectMapper objectMapper = new ObjectMapper();

        when(firestationService.updateFirestationMapping( "1509 Culver St",3)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.station").value(3));
    }
    /**
     * Test for updateFirestation endpoint when the address is not found.
     * @throws Exception
     */
    @Test
    void updateFirestation_shouldReturn404_whenAddressNotFound() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("Unknown", 2);
        ObjectMapper objectMapper = new ObjectMapper();
        when(firestationService.updateFirestationMapping("Unknown", 2)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
    @Test
    void updateFirestation_shouldReturn400_whenExceptionThrown() throws Exception {
        FirestationRequestDTO request = new FirestationRequestDTO("1509 Culver St", 3);
        ObjectMapper objectMapper = new ObjectMapper();

        when(firestationService.updateFirestationMapping("1509 Culver St", 3)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    /**
     * Test for deleteFirestation endpoint.
     * @throws Exception
     */
    @Test
    void deleteFirestation_shouldReturn200_whenSuccess() throws Exception {
        when(firestationService.deleteFirestationMapping("1509 Culver St")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk());
    }
    /**
     * Test for deleteFirestation endpoint when the address is not found.
     * @throws Exception
     */
    @Test
    void deleteFirestation_shouldReturn404_whenAddressNotFound() throws Exception {

                when(firestationService.deleteFirestationMapping("Unknown")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .param("address", "Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFirestation_shouldReturn400_whenExceptionThrown() throws Exception {
        when(firestationService.deleteFirestationMapping("1509 Culver St")).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.delete("/firestation")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isBadRequest());
    }



}
