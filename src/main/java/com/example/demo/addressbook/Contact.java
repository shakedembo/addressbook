package com.example.demo.addressbook;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
public @Data class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String email;
    private String name;
    private String phoneNumber;
    protected Contact() {}

    public Contact(String email, String name, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("Contact[id=`%d`, email=`%s`, name=`%s`, phone=`%s`]", id, email, email, phoneNumber);
    }
}
