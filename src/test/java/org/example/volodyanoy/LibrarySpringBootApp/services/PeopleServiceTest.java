package org.example.volodyanoy.LibrarySpringBootApp.services;


import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {
    @Mock
    PeopleRepository peopleRepository;

    @InjectMocks
    PeopleService peopleService;

    @Test
    public void testGetBooksInPersonPossession() {
        Person person = new Person();
        person.addBook(new Book());
        person.addBook(new Book());

        Mockito.when(peopleRepository.findById(1)).thenReturn(Optional.of(person));

        List<Book> seviceResultList = peopleService.getBooksInPersonPossession(1);

        Assertions.assertTrue(person.getBooks().containsAll(seviceResultList));
        Assertions.assertTrue(seviceResultList.containsAll(person.getBooks()));

    }

    @Test
    public void testEmptyGetBooksInPersonPossession() {
        Person person = new Person();
        Mockito.when(peopleRepository.findById(1)).thenReturn(Optional.of(person));
        Assertions.assertTrue(peopleService.getBooksInPersonPossession(1).isEmpty());

    }


}
