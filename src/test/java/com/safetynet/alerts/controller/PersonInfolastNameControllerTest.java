package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonInfolastNameDTO;
import com.safetynet.alerts.dto.ResidentsDTO;
import com.safetynet.alerts.service.PersonInfolastNameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonInfolastNameController.class)
class PersonInfolastNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonInfolastNameService personInfolastNameService;

    @Test
    void getPersonInfolastName_shouldReturnResidents_whenLastNameExists() throws Exception {
        // GIVEN
        String lastName = "Boyd";

        PersonInfolastNameDTO responseDTO = new PersonInfolastNameDTO(
                List.of(
                        new ResidentsDTO(
                                "John",
                                "Boyd",
                                "1509 Culver St",
                                "111-111",
                                40,
                                List.of("med1"),
                                List.of("peanut")
                        ),
                        new ResidentsDTO(
                                "Tenley",
                                "Boyd",
                                "1509 Culver St",
                                "222-222",
                                12,
                                List.of(),
                                List.of("dust")
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

    @Test
    void getPersonInfolastName_shouldReturnEmptyList_whenNoResult() throws Exception {
        // GIVEN
        String lastName = "Unknown";

        when(personInfolastNameService.getPersonInfoByLastName(lastName))
                .thenReturn(new PersonInfolastNameDTO(List.of()));

        // WHEN / THEN
        mockMvc.perform(get("/personInfolastName/lastName")
                        .param("lastName", lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residents").isArray())
                .andExpect(jsonPath("$.residents").isEmpty());
    }
}
