package org.example.volodyanoy.LibrarySpringBootApp.unit.services;


import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.AccountsRepository;
import org.example.volodyanoy.LibrarySpringBootApp.services.AccountService;
import org.example.volodyanoy.LibrarySpringBootApp.util.AccountUpdateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountUpdateValidatorUnitTest {
    @Mock
    private AccountsRepository accountsRepository;

    private AccountUpdateValidator accountUpdateValidator;
    private Errors errors;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountUpdateValidator = new AccountUpdateValidator(accountService);
    }

    @Test
    public void testValidateSameUsernameAndDifferentId() {
        String username = "Bob";

        Account account = new Account();
        account.setUsername(username);
        account.setId(1);

        Account updatedAccount = new Account();
        updatedAccount.setUsername(username);
        updatedAccount.setId(2);

        Mockito.when(accountsRepository.findByUsername(username)).thenReturn(Optional.of(account));
        errors = new BeanPropertyBindingResult(updatedAccount, "account");

        accountUpdateValidator.validate(updatedAccount, errors);
        Assertions.assertTrue(errors.hasErrors());

    }

    @Test
    public void testValidateSameUsernameAndSameId() {
        String username = "Bob";

        Account account = new Account();
        account.setUsername(username);
        account.setId(1);

        Account updatedAccount = new Account();
        updatedAccount.setUsername(username);
        updatedAccount.setId(1);

        Mockito.when(accountsRepository.findByUsername(username)).thenReturn(Optional.of(account));
        errors = new BeanPropertyBindingResult(updatedAccount, "account");

        accountUpdateValidator.validate(updatedAccount, errors);
        Assertions.assertFalse(errors.hasErrors());

    }

    @Test
    public void testValidateDifferentUsername() {

        String username = "Alice";
        Account updatedAccount = new Account();
        updatedAccount.setUsername(username);

        Mockito.when(accountsRepository.findByUsername(username)).thenReturn(Optional.empty());
        errors = new BeanPropertyBindingResult(updatedAccount, "account");

        accountUpdateValidator.validate(updatedAccount, errors);
        Assertions.assertFalse(errors.hasErrors());

    }


}
