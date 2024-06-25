package com.addressbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addressbook.exception.ResourceNotFoundException;
import com.addressbook.model.Contact;
import com.addressbook.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;

	public Contact addContact(Contact contact) {
		return contactRepository.save(contact);
	}

	public void removeContact(Long id) {
		Contact contact = getContact(id);
		if (contact == null) {
			throw new ResourceNotFoundException("Contact with id " + id + " not found");

		}
		contactRepository.deleteById(id);
	}

	public Contact getContact(Long id) {
		return contactRepository.findById(id).orElse(null);

	}

	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}
}