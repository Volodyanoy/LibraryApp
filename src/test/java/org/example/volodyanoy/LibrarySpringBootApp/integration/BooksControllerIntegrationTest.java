package org.example.volodyanoy.LibrarySpringBootApp.integration;


import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.repositories.BooksRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class BooksControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final BooksRepository booksRepository;

    @Autowired
    public BooksControllerIntegrationTest(MockMvc mockMvc, BooksRepository booksRepository) {
        this.mockMvc = mockMvc;
        this.booksRepository = booksRepository;
    }

    @BeforeEach
    public void setupDB(){
        booksRepository.deleteAll();
        booksRepository.save(new Book("Breaking bad1", "Jessie1", 1996));
        booksRepository.save(new Book("Breaking bad2", "Jessie2", 1997));
        booksRepository.save(new Book("Breaking bad3", "Jessie3", 1999));
    }

    @Test
    public void integrationTestGetAllBooks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/index"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", Matchers.hasSize(3)));


    }

}
