package org.example.volodyanoy.LibrarySpringBootApp.services;

import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountService {
    public final AccountsRepository accountsRepository;

    @Autowired
    public AccountService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public Account findByUsername(String username){
        Optional<Account> foundAccount = accountsRepository.findByUsername(username);
        return foundAccount.orElse(null);
    }
}
