package com.example.demo.addressbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddressBookServiceImpl implements AddressBookService {

    public final AddressBookDAO addressBookDAO;

    @Autowired
    public AddressBookServiceImpl(AddressBookDAO addressBookDAO) {
        this.addressBookDAO = addressBookDAO;
    }

    @Override
    public List<Contact> GetAll() {
        return addressBookDAO.GetAll();
    }

    @Override
    public Contact GetById(int id) {
        var contacts = addressBookDAO.GetById(id);
        if (contacts.size() == 1) {
            return contacts.get(0);
        } else if (contacts.isEmpty()) {
            throw new IllegalStateException(String.format("There is no contact with the corresponding ID: '%d'", id));
        }

        throw new InternalError(String.format("There is more than one contact with the corresponding ID: '%d'", id));
    }

    @Override
    public List<Contact> GetByName(String name) {
        return addressBookDAO.GetByName(name);
    }

    @Override
    public Contact GetByEmail(String email) {
        return addressBookDAO.GetByEmail(email);
    }

    @Override
    public int Create(Contact contact) {
        var existing = addressBookDAO.GetByEmail(contact.getEmail());
        if (existing != null) {
            contact.setId(existing.getId());
        }

        return addressBookDAO.CreateOrUpdate(contact);
    }

    @Override
    public Contact Update(Contact contact) {
        var existing = addressBookDAO.GetByEmail(contact.getEmail());
        if (existing == null) {
            throw new IllegalStateException(String.format(
                    "The contact with the corresponding email address '%s' doesn't exists.", contact.getEmail())
            );
        }

        contact.setId(existing.getId());
        addressBookDAO.CreateOrUpdate(contact);

        return contact;
    }
}
