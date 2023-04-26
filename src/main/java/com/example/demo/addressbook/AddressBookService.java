package com.example.demo.addressbook;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressBookService {
    List<Contact> GetAll();
    Contact GetById(int id);
    Contact GetByEmail(String email);
    List<Contact> GetByName(String name);
    int Create(Contact contact);
    Contact Update(Contact contact);
}
