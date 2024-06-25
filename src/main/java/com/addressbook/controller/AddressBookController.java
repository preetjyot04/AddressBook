package com.addressbook.controller;

import java.util.List;
import java.util.Set;

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

import com.addressbook.model.AddressBook;
import com.addressbook.model.Contact;
import com.addressbook.model.ResponseSuccess;
import com.addressbook.service.AddressBookService;

@RestController
@RequestMapping("/addressbooks")
public class AddressBookController {
	@Autowired
	private AddressBookService addressBookService;

	@PostMapping
	public ResponseEntity<AddressBook> addAddressBook(@RequestBody AddressBook addressBook) {
		return new ResponseEntity<>(addressBookService.addAddressBook(addressBook), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseSuccess> removeAddressBook(@PathVariable Long id) {
		addressBookService.removeAddressBook(id);
		return new ResponseEntity<>( new ResponseSuccess("Address Book Deleted"),  HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<AddressBook>> getAllAddressBooks() {
		return new ResponseEntity<>(addressBookService.getAllAddressBooks(), HttpStatus.OK);
	}

	@GetMapping("/unique-contacts")
	public ResponseEntity<Set<Contact>> getAllUniqueContacts() {
		return new ResponseEntity<>(addressBookService.getAllUniqueContacts(), HttpStatus.OK);
	}
}