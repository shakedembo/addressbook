package com.example.demo.addressbook;

import com.example.demo.DemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AddressBookServiceImpl implements AddressBookService {

    private static final String ADDRESSBOOK_PRODUCER_TOPIC = "addressbook-producer-topic";
    public final AddressBookDAO addressBookDAO;
    private final KafkaTemplate<String, KafkaMessage<Contact>> producer;

    @Autowired
    public AddressBookServiceImpl(AddressBookDAO addressBookDAO, KafkaTemplate<String, KafkaMessage<Contact>> producer) {
        this.addressBookDAO = addressBookDAO;
        this.producer = producer;
    }

    @Override
    public List<Contact> GetAll() {
        List<Contact> contacts = addressBookDAO.GetAll();
        contacts.forEach(c -> {
            producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                    new KafkaMessage<>(c, LocalDateTime.now(), "getAll", CommandResult.SUCCESS, ""));
        });
        return contacts;
    }

    @Override
    public Contact GetById(int id) {
        var contacts = addressBookDAO.GetById(id);
        if (contacts.size() == 1) {
            producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                    new KafkaMessage<>(contacts.get(0), LocalDateTime.now(), "GetById", CommandResult.SUCCESS, ""));
            return contacts.get(0);
        } else if (contacts.isEmpty()) {
            producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                    new KafkaMessage<>(null, LocalDateTime.now(), "GetById", CommandResult.FAILURE, String.valueOf(id)));
            throw new IllegalStateException(String.format("There is no contact with the corresponding ID: '%d'", id));
        }
        producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                new KafkaMessage<>(null, LocalDateTime.now(), "GetById", CommandResult.FAILURE, String.valueOf(id)));
        throw new InternalError(String.format("There is more than one contact with the corresponding ID: '%d'", id));
    }

    @Override
    public List<Contact> GetByName(String name) {
        List<Contact> contacts = addressBookDAO.GetByName(name);
        contacts.forEach(c -> {
            producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                    new KafkaMessage<>(c, LocalDateTime.now(), "GetByName", CommandResult.SUCCESS, ""));
        });

        return contacts;
    }

    @Override
    public Contact GetByEmail(String email) {
        Contact contact = addressBookDAO.GetByEmail(email);
        producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                new KafkaMessage<>(contact, LocalDateTime.now(), "GetByEmail", CommandResult.SUCCESS, ""));
        return contact;
    }

    @Override
    public int Create(Contact contact) {
        var existing = addressBookDAO.GetByEmail(contact.getEmail());
        if (existing != null) {
            contact.setId(existing.getId());
        }
        var res = addressBookDAO.CreateOrUpdate(contact);
        producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                new KafkaMessage<>(contact, LocalDateTime.now(), "Create", CommandResult.SUCCESS, ""))
                .whenComplete((sendRes, y) -> {
            DemoApplication.log.info(String.format("The kafkaSendResult: '%s'", sendRes.toString()));
        });


        return res;
    }

    @Override
    public Contact Update(Contact contact) {
        var existing = addressBookDAO.GetByEmail(contact.getEmail());
        if (existing == null) {
            producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                    new KafkaMessage<>(contact, LocalDateTime.now(), "Update", CommandResult.FAILURE, "doesn't exists"));
            throw new IllegalStateException(String.format(
                    "The contact with the corresponding email address '%s' doesn't exists.", contact.getEmail())
            );
        }

        contact.setId(existing.getId());
        addressBookDAO.CreateOrUpdate(contact);

        producer.send(ADDRESSBOOK_PRODUCER_TOPIC,
                new KafkaMessage<>(contact, LocalDateTime.now(), "Update", CommandResult.SUCCESS, ""));

        return contact;
    }
}
