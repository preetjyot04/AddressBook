package com.addressbook.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.addressbook.exception.ResourceNotFoundException;
import com.addressbook.model.Contact;
import com.addressbook.repository.ContactRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

	@InjectMocks
	private ContactService contactService;

	@Mock
	private ContactRepository contactRepository;

	@Test
	public void testAddContact() {
		Contact contact = new Contact();
		contact.setName("John Doe");
		contact.setPhoneNumber("1234567890");

		when(contactRepository.save(any(Contact.class))).thenReturn(contact);

		Contact savedContact = contactService.addContact(contact);
		assertNotNull(savedContact);
		assertEquals("John Doe", savedContact.getName());
		assertEquals("1234567890", savedContact.getPhoneNumber());
	}

	@Test
	void testRemoveContact() {
		Long idToRemove = 1L;
		Contact existingContact = new Contact();
		existingContact.setId(idToRemove);

		when(contactRepository.findById(idToRemove)).thenReturn(Optional.of(existingContact));

		assertDoesNotThrow(() -> contactService.removeContact(idToRemove));

		verify(contactRepository, times(1)).deleteById(idToRemove);
	}

	@Test
	void testRemoveContactResourceNotFound() {

		Long idToRemove = 1L;
		when(contactRepository.findById(idToRemove)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			contactService.removeContact(idToRemove);
		});
		assertEquals("Contact with id " + idToRemove + " not found", exception.getMessage());
		verify(contactRepository, never()).deleteById(idToRemove);

	}

	@Test
	public void testGetAllContacts() {
		List<Contact> contacts = Arrays.asList(new Contact(1L, "John Doe", "1234567890"),
				new Contact(2L, "Jane Doe", "0987654321"));

		when(contactRepository.findAll()).thenReturn(contacts);

		List<Contact> allContacts = contactService.getAllContacts();
		assertEquals(2, allContacts.size());
		assertEquals("John Doe", allContacts.get(0).getName());
		assertEquals("Jane Doe", allContacts.get(1).getName());
	}
}