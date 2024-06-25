package com.addressbook.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.addressbook.model.AddressBook;
import com.addressbook.model.Contact;
import com.addressbook.service.AddressBookService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressBookController.class)
public class AddressBookControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressBookService addressBookService;

	@Test
	public void testAddAddressBook() throws Exception {
		AddressBook addressBook = new AddressBook();
		addressBook.setId(1L);
		addressBook.setName("Family");

		Mockito.when(addressBookService.addAddressBook(Mockito.any(AddressBook.class))).thenReturn(addressBook);

		mockMvc.perform(post("/addressbooks").contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Family\"}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Family")));
	}

	@Test
	public void testRemoveAddressBook() throws Exception {
		Long addressBookId = 1L;
		Mockito.doNothing().when(addressBookService).removeAddressBook(addressBookId);

		mockMvc.perform(delete("/addressbooks/{id}", addressBookId)).andExpect(status().isOk());
	}

	@Test
	public void testGetAllAddressBooks() throws Exception {

		Set<Contact> contactList = new HashSet<>();
		contactList.add(new Contact(1L, "Adam", "987654320"));
		contactList.add(new Contact(2L, "Vick", "9876543210"));

		List<AddressBook> addressBooks = Arrays.asList(new AddressBook(1L, "Family", contactList),
				new AddressBook(2L, "Work", contactList));

		Mockito.when(addressBookService.getAllAddressBooks()).thenReturn(addressBooks);

		mockMvc.perform(get("/addressbooks")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("Family")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].name", is("Work")));

	}

	@Test
	public void testGetAllUniqueContacts() throws Exception {
		Contact contact1 = new Contact(1L, "John Doe", "1234567890");
		Contact contact2 = new Contact(2L, "Jane Doe", "0987654321");


		Mockito.when(addressBookService.getAllUniqueContacts())
				.thenReturn(new HashSet<>(Arrays.asList(contact1, contact2)));

		mockMvc.perform(get("/addressbooks/unique-contacts")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

}
