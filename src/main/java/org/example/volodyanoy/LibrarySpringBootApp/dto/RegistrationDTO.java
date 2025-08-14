package org.example.volodyanoy.LibrarySpringBootApp.dto;

import jakarta.validation.Valid;
import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;

public class RegistrationDTO {
    @Valid
    private Person person;
    @Valid
    private Account account;

    RegistrationDTO(){}

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
