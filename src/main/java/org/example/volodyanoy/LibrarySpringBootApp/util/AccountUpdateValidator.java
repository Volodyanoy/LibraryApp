package org.example.volodyanoy.LibrarySpringBootApp.util;

import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountUpdateValidator implements Validator {
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(AccountUpdateValidator.class);

    @Autowired
    public AccountUpdateValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Account account = (Account) target;
        logger.info("Account username {} and id {}", account.getUsername(), account.getId());
        Account foundedAccount = accountService.findByUsername(account.getUsername());

        if (foundedAccount != null && foundedAccount.getId() != account.getId()) {
            logger.info("Founded username {} and id {}", foundedAccount.getUsername(), foundedAccount.getId());
            errors.reject("", "Внимание! Аккаунт с таким username уже существует");
        }

    }

}
