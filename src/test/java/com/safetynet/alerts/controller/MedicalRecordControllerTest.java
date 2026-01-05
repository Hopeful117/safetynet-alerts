package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for MedicalRecordController.
 */
@WebMvcTest(MedicalRecordController.class)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private MedicalRecordDTO dto;
    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        dto = new MedicalRecordDTO(
                "John",
                "Doe",
                "01/01/1990",
                List.of("med1"),
                List.of("allergy1")
        );

        medicalRecord = new MedicalRecord(
                "John",
                "Doe",
                "01/01/1990",
                List.of("med1"),
                List.of("allergy1")
        );
    }

    /**
     * Test for addMedicalRecord endpoint.
     * @throws Exception
     */
    @Test
    void addMedicalRecord_shouldReturnCreated() throws Exception {
        when(medicalRecordService.addMedicalRecord(any(MedicalRecordDTO.class)))
                .thenReturn(medicalRecord);

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthdate": "01/01/1990",
                                  "medications": ["med1"],
                                  "allergies": ["allergy1"]
                                }
                                """))
                .andExpect(status().isCreated());

        verify(medicalRecordService).addMedicalRecord(any(MedicalRecordDTO.class));
    }

    /**
     * Test for addMedicalRecord endpoint when an exception is thrown.
     * @throws Exception
     */
    @Test
    void addMedicalRecord_shouldReturnBadRequest_whenExceptionThrown() throws Exception {
        when(medicalRecordService.addMedicalRecord(any()))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
    /**
     * Test for updateMedicalRecord endpoint.
     * @throws Exception
     */
    @Test
    void updateMedicalRecord_shouldReturnOk() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecordDTO.class)))
                .thenReturn(medicalRecord);

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "birthdate": "02/02/1995",
                                  "medications": ["med2"],
                                  "allergies": []
                                }
                                """))
                .andExpect(status().isOk());

        verify(medicalRecordService).updateMedicalRecord(any(MedicalRecordDTO.class));
    }
    /**
     * Test for updateMedicalRecord endpoint when an exception is thrown.
     * @throws Exception
     */
    @Test
    void updateMedicalRecord_shouldReturnBadRequest_whenExceptionThrown() throws Exception {
        when(medicalRecordService.updateMedicalRecord(any()))
                .thenThrow(new IllegalArgumentException("Invalid update"));

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
    /**
     * Test for deleteMedicalRecord endpoint.
     * @throws Exception
     */
    @Test
    void deleteMedicalRecord_shouldReturnNoContent() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("John", "Doe"))
                .thenReturn(true);

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNoContent());

        verify(medicalRecordService).deleteMedicalRecord("John", "Doe");
    }
    /**
     * Test for deleteMedicalRecord endpoint when record not found.
     * @throws Exception
     */
    @Test
    void deleteMedicalRecord_shouldReturnNotFound() throws Exception {
        when(medicalRecordService.deleteMedicalRecord("John", "Doe"))
                .thenReturn(false);

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNotFound());
    }
    /**
     * Test for deleteMedicalRecord endpoint when an exception is thrown.
     * @throws Exception
     */
    @Test
    void deleteMedicalRecord_shouldReturnBadRequest_whenExceptionThrown() throws Exception {
        when(medicalRecordService.deleteMedicalRecord(any(), any()))
                .thenThrow(new IllegalArgumentException("Error"));

        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isBadRequest());
    }
}

