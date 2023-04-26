package com.example.demo.addressbook;


import com.example.demo.DemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/address-book")
public class AddressBookController {
    private final AddressBookService addressBookService;

    @Autowired
    public AddressBookController(AddressBookService addressBookService) {
        DemoApplication.log.info(String.format("initializing controller: '%s'", AddressBookController.class.getName()));
        this.addressBookService = addressBookService;
    }

    @GetMapping("/")
    public String Hello() {
        return String.format("Hello from '%s'", this.getClass().getName());
    }
    @GetMapping("/getAll")
    public List<Contact> GetAll() {
        DemoApplication.log.info("received get-all request");
        return addressBookService.GetAll();
    }

    @GetMapping("/getById")
    public Contact GetById(@RequestParam int id) {
        DemoApplication.log.info(String.format("""
                received get-by-id request:
                  id: '%d'""", id));
        return addressBookService.GetById(id);
    }
    
    @GetMapping("/getByName")
    public List<Contact> GetByName(@RequestParam String name) {
        DemoApplication.log.info(String.format("""
                received get-by-name request:
                  name: '%s'""", name));
        return addressBookService.GetByName(name);
    }
    @GetMapping("/getByEmail")
    public Contact GetByEmail(@RequestParam String email) {
        DemoApplication.log.info(String.format("""
                received get-by-email request:
                  email: '%s'""", email));
        return addressBookService.GetByEmail(email);
    }

    @PostMapping("/create")
    public int Create(@RequestBody Contact contact) {
        DemoApplication.log.info(String.format("""
                received create request:
                  '%s'""", contact));
        return addressBookService.Create(contact);
    }

    @PostMapping("/update")
    public Contact Update(@RequestBody Contact contact) {
        DemoApplication.log.info(String.format("""
                received update request:
                    '%s'""", contact));
        
        return addressBookService.Update(contact);
    }
}
