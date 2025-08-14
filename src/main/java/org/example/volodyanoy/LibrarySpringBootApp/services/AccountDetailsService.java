package org.example.volodyanoy.LibrarySpringBootApp.services;


import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.AccountsRepository;
import org.example.volodyanoy.LibrarySpringBootApp.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {
    private final AccountsRepository accountsRepository;

    @Autowired
    public AccountDetailsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountsRepository.findByUsername(username);
        if(account.isEmpty()){
            throw  new UsernameNotFoundException("User not found!");
        }
        return new AccountDetails(account.get());

    }
}
