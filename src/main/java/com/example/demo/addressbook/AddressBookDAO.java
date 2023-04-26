package com.example.demo.addressbook;

import java.util.List;

public interface AddressBookDAO {
    List<Contact> GetAll();
    List<Contact> GetByName(String name);
    List<Contact> GetById(int id);
    Contact GetByEmail(String email);

    int CreateOrUpdate(Contact contact);
}
