package com.addressbook.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.addressbook.exception.ResourceNotFoundException;
import com.addressbook.model.AddressBook;
import com.addressbook.model.Contact;
import com.addressbook.repository.AddressBookRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressBookServiceTest {

	@InjectMocks
	private AddressBookService addressBookService;

	@Mock
	private AddressBookRepository addressBookRepository;

	@Test
	public void testAddAddressBook() {
		AddressBook addressBook = new AddressBook();
		addressBook.setName("Family");

		when(addressBookRepository.save(any(AddressBook.class))).thenReturn(addressBook);

		AddressBook savedAddressBook = addressBookService.addAddressBook(addressBook);
		assertNotNull(savedAddressBook);
		assertEquals("Family", savedAddressBook.getName());
	}

	@Test
	void testRemoveAddressBook() {
		Long idToRemove = 1L;
		AddressBook existingAddressBook = new AddressBook();
		existingAddressBook.setId(idToRemove);

		when(addressBookRepository.findById(idToRemove)).thenReturn(Optional.of(existingAddressBook));

		assertDoesNotThrow(() -> addressBookService.removeAddressBook(idToRemove));

		verify(addressBookRepository, times(1)).deleteById(idToRemove);
	}

	@Test
	void testRemoveAddressBookResourceNotFound() {
		Long idToRemove = 1L;

		when(addressBookRepository.findById(idToRemove)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			addressBookService.removeAddressBook(idToRemove);
		});
		assertEquals("Address Book with id " + idToRemove + " not found", exception.getMessage());
		verify(addressBookRepository, never()).deleteById(idToRemove);
	}

	@Test
	public void testGetAllAddressBooks() {
		Set<Contact> contactList = new HashSet<>();
		contactList.add(new Contact(1L, "Adam", "987654320"));
		contactList.add(new Contact(2L, "Vick", "9876543210"));

		List<AddressBook> addressBooks = Arrays.asList(new AddressBook(1L, "Family", contactList),
				new AddressBook(1L, "Work", contactList));

		when(addressBookRepository.findAll()).thenReturn(addressBooks);

		List<AddressBook> allAddressBooks = addressBookService.getAllAddressBooks();
		assertEquals(2, allAddressBooks.size());
		assertEquals("Family", allAddressBooks.get(0).getName());
		assertEquals("Work", allAddressBooks.get(1).getName());

	}

	@Test
	public void testGetAllUniqueContacts() {
		Contact contact1 = new Contact(1L, "John Doe", "1234567890");
		Contact contact2 = new Contact(2L, "Jane Doe", "0987654321");

		AddressBook addressBook1 = new AddressBook();
		addressBook1.setName("Family");
		addressBook1.setContacts(new HashSet<>(Arrays.asList(contact1)));

		AddressBook addressBook2 = new AddressBook();
		addressBook2.setName("Work");
		addressBook2.setContacts(new HashSet<>(Arrays.asList(contact1, contact2)));

		when(addressBookRepository.findAll()).thenReturn(Arrays.asList(addressBook1, addressBook2));

		Set<Contact> uniqueContacts = addressBookService.getAllUniqueContacts();
		assertEquals(2, uniqueContacts.size());
		assertTrue(uniqueContacts.contains(contact1));
		assertTrue(uniqueContacts.contains(contact2));
	}
}
