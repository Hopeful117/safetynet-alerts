package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.PersonRequestDTO;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        PersonRequestDTO dto = new PersonRequestDTO(
                "John",
                "Doe",
                "123 Main St",
                "Culver",
                "97451",
                "111-111",
                "john@doe.com"
        );
    }

    /**
     * Test for addPerson endpoint.
     *
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    void addPerson_shouldReturnCreated() throws Exception {


        when(personService.addPerson(any(PersonRequestDTO.class)))
                .thenReturn(true);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "address": "123 Main St",
                                    "city": "Culver",
                                    "zip": "97451",
                                    "phone": "111-111",
                                    "email": "john@doe.com"
                                }
                                """))
                .andExpect(status().isCreated());


    }

    /**
     * Test for addPerson endpoint when an exception is thrown.
     *
     * @throws Exception if an error occurs during the test execution.
     */

    @Test
    void addPerson_shouldReturnConflict_whenPersonAlreadyExists() throws Exception {

        when(personService.addPerson(any(PersonRequestDTO.class)))
                .thenReturn(false);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "address": "123 Main St",
                                    "city": "Culver",
                                    "zip": "97451",
                                    "phone": "111-111",
                                    "email": "john@doe.com"
                                }
                                """))
                .andExpect(status().isConflict());
    }

    /**
     * Test for addPerson endpoint when an exception is thrown.
     *
     * @throws Exception if an error occurs during the test execution.
     */
    @Test
    void addPerson_shouldReturnBadRequest_whenExceptionThrown() throws Exception {


        when(personService.addPerson(any(PersonRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid input"));

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "address": "123 Main St",
                                    "city": "Culver",
                                    "zip": "97451",
                                    "phone": "111-111",
                                    "email": "john@doe.com"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for updatePerson endpoint.
     *
     * @throws Exception
     */
    @Test
    void updatePerson_shouldReturnAccepted() throws Exception {


        when(personService.updatePerson(any(PersonRequestDTO.class)))
                .thenReturn(true
                );

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "address": "123 Main St",
                                    "city": "Culver",
                                    "zip": "97451",
                                    "phone": "111-111",
                                    "email": "john@doe.com"
                                }
                                """))
                .andExpect(status().isAccepted());

        verify(personService).updatePerson(any(PersonRequestDTO.class));
        ;
    }

    /**
     * Test for updatePerson endpoint when person is not found.
     *
     * @throws Exception
     */
    @Test
    void updatePerson_shouldReturnNotFound_whenPersonNotFound() throws Exception {


        when(personService.updatePerson(any(PersonRequestDTO.class)))
                .thenReturn(false);


        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "address": "123 Main St",
                                    "city": "Culver",
                                    "zip": "97451",
                                    "phone": "111-111",
                                    "email": "john@doe.com"
                                }
                                """))
                .andExpect(status().isNotFound());
    }

    /**
     * Test for updatePerson endpoint when an exception is thrown.
     *
     * @throws Exception
     */
    @Test
    void updatePerson_shouldReturnBadRequest_whenExceptionThrown() throws Exception {

        when(personService.updatePerson(any(PersonRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid input"));
        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "John",
                                    "lastName": "Doe",
                                    "address": "123 Main St",
                                    "city": "Culver",
                                    "zip": "97451",
                                    "phone": "111-111",
                                    "email": "john@doe.com"
                                    }
                                """))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test for deletePerson endpoint.
     * Test for deletePerson endpoint when person does not exist.
     *
     * @throws Exception
     */
    @Test
    void deletePerson_shouldReturnNotFound_whenPersonDoesNotExist() throws Exception {
        when(personService.deletePerson(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(delete("/person")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isNotFound());

        verify(personService).deletePerson("John", "Doe");
    }

    /**
     * Test for deletePerson endpoint when an exception is thrown.
     *
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

