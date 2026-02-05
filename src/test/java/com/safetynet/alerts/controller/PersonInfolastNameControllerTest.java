package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.PersonInfolastNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Test class for PersonInfolastNameController.
 */
@WebMvcTest(PersonInfolastNameController.class)
class PersonInfolastNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonInfolastNameService personInfolastNameService;
/**
     * Test for getPersonInfolastName endpoint.
     * @throws Exception if an error occurs during the request
     */
    @Test
    void getPersonInfolastName_shouldReturnResidents_whenLastNameExists() throws Exception {
        // GIVEN
        String lastName = "Boyd";

        ResidentsDTO responseDTO = new ResidentsDTO(
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
        );




        when(personInfolastNameService.getPersonInfoByLastName(lastName))
                .thenReturn(responseDTO);

        // WHEN / THEN
        mockMvc.perform(get("/personInfolastName/lastName")
                        .param("lastName", lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents").isArray())
                .andExpect(jsonPath("$.residents.length()").value(2))
                .andExpect(jsonPath("$.residents[0].firstName").value("John"))
                .andExpect(jsonPath("$.residents[0].lastName").value("Boyd"))
                .andExpect(jsonPath("$.residents[0].age").value(40))
                .andExpect(jsonPath("$.residents[1].firstName").value("Tenley"))
                .andExpect(jsonPath("$.residents[1].age").value(12));
    }
/**
     * Test for getPersonInfolastName endpoint when no results are found.
     * @throws Exception if an error occurs during the request
     */
    @Test
    void getPersonInfolastName_shouldReturnEmptyList_whenNoResult() throws Exception {
        // GIVEN
        String lastName = "Unknown";

        when(personInfolastNameService.getPersonInfoByLastName(lastName))
                .thenReturn(new ResidentsDTO(List.of()));

        // WHEN / THEN
        mockMvc.perform(get("/personInfolastName/lastName")
                        .param("lastName", lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents").isArray())
                .andExpect(jsonPath("$.residents").isEmpty());
    }
}
