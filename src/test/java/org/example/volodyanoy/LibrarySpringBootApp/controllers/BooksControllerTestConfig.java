package org.example.volodyanoy.LibrarySpringBootApp.controllers;

import org.example.volodyanoy.LibrarySpringBootApp.services.BooksService;
import org.example.volodyanoy.LibrarySpringBootApp.services.PeopleService;
import org.example.volodyanoy.LibrarySpringBootApp.util.BookValidator;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class BooksControllerTestConfig {
    @Bean
    public BooksService booksService(){
        return Mockito.mock(BooksService.class);
    }

    @Bean
    public PeopleService peopleService(){
        return Mockito.mock(PeopleService.class);
    }

    @Bean
    public BookValidator bookValidator(){
        return Mockito.mock(BookValidator.class);
    }

}
