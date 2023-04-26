package com.example.demo.addressbook;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findByName(String name);

    Contact findByEmail(String email);
}
