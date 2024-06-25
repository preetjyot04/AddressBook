package com.addressbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.addressbook.model.Contact;
import com.addressbook.model.ResponseSuccess;
import com.addressbook.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	@Autowired
	private ContactService contactService;

	@PostMapping
	public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
		return new ResponseEntity<>(contactService.addContact(contact), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseSuccess> removeContact(@PathVariable Long id) {
		contactService.removeContact(id);
		return new ResponseEntity<>(new ResponseSuccess("Contact deleted"), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Contact>> getAllContacts() {
		return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
	}
}