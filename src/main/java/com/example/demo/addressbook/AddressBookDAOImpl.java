package com.example.demo.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class AddressBookDAOImpl implements AddressBookDAO {

    @Autowired
    private final ContactsRepository contacts;

    public AddressBookDAOImpl(ContactsRepository contacts) {
        this.contacts = contacts;
    }

    @Override
    public List<Contact> GetAll() {
        return StreamSupport.stream(this.contacts.findAll().spliterator(), true).toList();
    }

    @Override
    public List<Contact> GetByName(String name) {
        return contacts.findByName(name);
    }

    @Override
    public List<Contact> GetById(int id) {
        var contact = contacts.findById(id);
        return contact.map(List::of).orElseGet(List::of);
    }

    @Override
    public Contact GetByEmail(String email) {
        return contacts.findByEmail(email);
    }

    @Override
    public int CreateOrUpdate(Contact contact) {
        return contacts.save(contact).getId();
    }
}
