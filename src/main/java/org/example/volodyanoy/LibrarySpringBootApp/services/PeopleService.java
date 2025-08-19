package org.example.volodyanoy.LibrarySpringBootApp.services;

import org.example.volodyanoy.LibrarySpringBootApp.dto.RegistrationDTO;
import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.AccountsRepository;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(PeopleService.class);


    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> findAll(){
        return peopleRepository.findAllWithAccounts();
    }

    public List<Person> findAllWithBooks(){
        return peopleRepository.findAllWithBooksAndAccounts();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
        logger.info("Успешно добавлен человек {}", person);
    }

    @Transactional
    public void update(int id, RegistrationDTO registrationDTO){
        Person person = peopleRepository.findById(id).orElseThrow(() -> new RuntimeException("Person with id " + id + " not found"));
        Account account = person.getAccount();

        person.setName(registrationDTO.getPerson().getName());
        person.setYearOfBirth(registrationDTO.getPerson().getYearOfBirth());

        account.setUsername(registrationDTO.getAccount().getUsername());
        account.setPassword(passwordEncoder.encode(registrationDTO.getAccount().getPassword()));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void delete(int id){
        List<Book> books = getBooksInPersonPossession(id);
        for(Book book: books){
            book.setOwner(null);
            book.setDateOfBookAssignment(null);
        }
        peopleRepository.deleteById(id);
    }

    public List<Book> getBooksInPersonPossession(int id){
        return peopleRepository.findById(id)
                .map(person -> {
                    Hibernate.initialize(person.getBooks());
                    return person.getBooks();
                })
                .orElse(Collections.emptyList());

    }

}
