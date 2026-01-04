package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addPerson_shouldReturnCreated() throws Exception {
        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                "123 Main St",
                "Culver",
                "97451",
                "111-111",
                "john@doe.com"
        );

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(personService).addPerson(any(PersonRequestDTO.class));
    }

    @Test
    void addPerson_shouldReturnBadRequest_whenExceptionThrown() throws Exception {
        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                null,
                "Culver",
                "97451",
                "111-111",
                "john@doe.com"
        );

        doThrow(new IllegalArgumentException("Invalid data"))
                .when(personService).addPerson(any());

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updatePerson_shouldReturnOk() throws Exception {
        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                "456 New St",
                "Culver",
                "97451",
                "222-222",
                "john@new.com"
        );

        when(personService.updatePerson(dto))
                .thenReturn(new Person(
                        "John", "Doe", "456 New St",
                        "Culver", "97451", "222-222", "john@new.com"
                ));

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(personService).updatePerson(any(PersonRequestDTO.class));;
    }

    @Test
    void updatePerson_shouldReturnBadRequest_whenExceptionThrown() throws Exception {
        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                "456 New St",
                "Culver",
                "97451",
                "222-222",
                "john@new.com"
        );

        when(personService.updatePerson(any(PersonRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid update"));



        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deletePerson_shouldReturnNoContent() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNoContent());

        verify(personService).deletePerson("John", "Doe");
    }

    @Test
    void deletePerson_shouldReturnNotFound_whenPersonDoesNotExist() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(false);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNotFound());

        verify(personService).deletePerson("John", "Doe");
    }

    @Test
    void deletePerson_shouldReturnBadRequest_whenExceptionThrown() throws Exception {
        when(personService.deletePerson(anyString(), anyString()))
                .thenThrow(new IllegalArgumentException("Invalid input"));

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isBadRequest());
    }


}

