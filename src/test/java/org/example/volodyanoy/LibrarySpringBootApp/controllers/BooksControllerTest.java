package org.example.volodyanoy.LibrarySpringBootApp.controllers;

import org.example.volodyanoy.LibrarySpringBootApp.LibrarySpringBootAppApplication;
import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.services.BooksService;
import org.example.volodyanoy.LibrarySpringBootApp.services.PeopleService;
import org.example.volodyanoy.LibrarySpringBootApp.util.BookValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(BooksControllerTestConfig.class)
@WebMvcTest(controllers = BooksController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class BooksControllerTest {

    private final MockMvc mockMvc;
    private final BooksService booksService;

    @Autowired
    public BooksControllerTest(MockMvc mockMvc, BooksService booksService) {
        this.mockMvc = mockMvc;
        this.booksService = booksService;
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = List.of(new Book("Breaking bad", "Jessie", 1999));
        Mockito.when(booksService.findAll()).thenReturn(books);


        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/index"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", books));

    }


}
