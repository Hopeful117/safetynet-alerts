package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for PersonController.
 */
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;
/**
     * Test for addPerson endpoint.
     * @throws Exception
     */
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


    }
/**
     * Test for addPerson endpoint when an exception is thrown.
     * @throws Exception
     */
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
/**
     * Test for updatePerson endpoint.
     * @throws Exception
     */
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
                .thenReturn(true
                );

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(personService).updatePerson(any(PersonRequestDTO.class));;
    }
/**
     * Test for updatePerson endpoint when an exception is thrown.
     * @throws Exception
     */
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
/**
     * Test for deletePerson endpoint.
     * @throws Exception
     */
    @Test
    void deletePerson_shouldReturnNoContent() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(true);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNoContent());

        verify(personService).deletePerson("John", "Doe");
    }
/**
     * Test for deletePerson endpoint when person does not exist.
     * @throws Exception
     */
    @Test
    void deletePerson_shouldReturnNotFound_whenPersonDoesNotExist() throws Exception {
        when(personService.deletePerson("John", "Doe")).thenReturn(false);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNotFound());

        verify(personService).deletePerson("John", "Doe");
    }
/**
     * Test for deletePerson endpoint when an exception is thrown.
     * @throws Exception
     */
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

