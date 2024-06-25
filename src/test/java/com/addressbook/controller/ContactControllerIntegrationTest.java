package com.addressbook.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.addressbook.model.Contact;
import com.addressbook.service.ContactService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
public class ContactControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @Test
    public void testAddContact() throws Exception {
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setName("John Doe");
        contact.setPhoneNumber("1234567890");

        Mockito.when(contactService.addContact(Mockito.any(Contact.class))).thenReturn(contact);

        mockMvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Doe\", \"phoneNumber\": \"1234567890\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")));
    }

    @Test
    public void testRemoveContact() throws Exception {
        Long contactId = 1L;
        Mockito.doNothing().when(contactService).removeContact(contactId);

        mockMvc.perform(delete("/contacts/{id}", contactId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllContacts() throws Exception {
        List<Contact> contacts = Arrays.asList(
                new Contact(1L, "John Doe", "1234567890"),
                new Contact(2L, "Jane Doe", "0987654321")
        );

        Mockito.when(contactService.getAllContacts()).thenReturn(contacts);

        mockMvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[0].phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")))
                .andExpect(jsonPath("$[1].phoneNumber", is("0987654321")));
    }
}
