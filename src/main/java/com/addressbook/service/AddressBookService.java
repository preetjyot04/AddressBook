package com.addressbook.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.exception.ResourceNotFoundException;
import com.addressbook.model.AddressBook;
import com.addressbook.model.Contact;
import com.addressbook.repository.AddressBookRepository;

@Service
public class AddressBookService {
	@Autowired
	private AddressBookRepository addressBookRepository;

	public AddressBook addAddressBook(AddressBook addressBook) {
		return addressBookRepository.save(addressBook);
	}

	public void removeAddressBook(Long id) {
		AddressBook address = getAddressBook(id);
		if(address==null ) {
            throw new ResourceNotFoundException("Address Book with id " + id + " not found");
		}
		addressBookRepository.deleteById(id);
	}

	public AddressBook getAddressBook(Long id) {
		AddressBook address = addressBookRepository.findById(id).orElse(null);

		return address;

	}

	public List<AddressBook> getAllAddressBooks() {
		return addressBookRepository.findAll();
	}

	public Set<Contact> getAllUniqueContacts() {
		List<AddressBook> addressBooks = addressBookRepository.findAll();
		Set<Contact> uniqueContacts = new HashSet<>();
		for (AddressBook addressBook : addressBooks) {
			uniqueContacts.addAll(addressBook.getContacts());
		}
		return uniqueContacts;
	}
}