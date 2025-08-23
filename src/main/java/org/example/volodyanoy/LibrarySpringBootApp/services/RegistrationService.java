package org.example.volodyanoy.LibrarySpringBootApp.services;

import org.example.volodyanoy.LibrarySpringBootApp.dto.RegistrationDTO;
import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(AccountsRepository accountsRepository, PasswordEncoder passwordEncoder) {
        this.accountsRepository = accountsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegistrationDTO registrationDTO) {
        Account account = registrationDTO.getAccount();
        Person person = registrationDTO.getPerson();
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole("ROLE_USER");

        person.setAccount(account);
        account.setPerson(person);

        accountsRepository.save(account);

    }
}
