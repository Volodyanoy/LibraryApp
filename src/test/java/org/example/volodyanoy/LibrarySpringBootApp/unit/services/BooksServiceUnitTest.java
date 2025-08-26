package org.example.volodyanoy.LibrarySpringBootApp.unit.services;


import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.BooksRepository;
import org.example.volodyanoy.LibrarySpringBootApp.services.BooksService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BooksServiceUnitTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksService booksService;

    @Test
    void testAssignBookC() {
        Book book = new Book();
        Person oldOwner = new Person();
        Person newOnwer = new Person();
        book.setOwner(oldOwner);
        oldOwner.addBook(book);

        Mockito.when(booksRepository.findById(1)).thenReturn(Optional.of(book));

        booksService.assignBook(1, newOnwer);
        Assertions.assertFalse(oldOwner.getBooks().contains(book));
        Assertions.assertTrue(newOnwer.getBooks().contains(book));
    }

    @Test
    void testReleaseBook() {
        Book book = new Book();
        Person owner = new Person();
        book.setOwner(owner);
        owner.addBook(book);

        Mockito.when(booksRepository.findById(1)).thenReturn(Optional.of(book));

        booksService.releaseBook(1);
        Assertions.assertFalse(owner.getBooks().contains(book));
        Assertions.assertNull(book.getOwner());

    }


}
