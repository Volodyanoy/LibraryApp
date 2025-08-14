package org.example.volodyanoy.LibrarySpringBootApp.services;

import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.AccountsRepository;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;
    private final PeopleRepository peopleRepository;

    @Autowired
    public RegistrationService(AccountsRepository accountsRepository, PasswordEncoder passwordEncoder, PeopleRepository peopleRepository) {
        this.accountsRepository = accountsRepository;
        this.passwordEncoder = passwordEncoder;
        this.peopleRepository = peopleRepository;
    }

    public void register(Account account, Person person){
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole("ROLE_USER");

        account.setPerson(person);
        person.setAccount(account);

        accountsRepository.save(account);
        peopleRepository.save(person);
    }
}
