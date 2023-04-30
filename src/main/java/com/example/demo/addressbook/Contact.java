package com.example.demo.addressbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Contact {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonProperty
    @Column(unique = true)
    private String email;

    @JsonProperty
    private String name;

    @JsonProperty
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
