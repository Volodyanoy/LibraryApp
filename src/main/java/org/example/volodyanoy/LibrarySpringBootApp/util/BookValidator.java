package org.example.volodyanoy.LibrarySpringBootApp.util;

import org.example.volodyanoy.LibrarySpringBootApp.dao.BookDAO;
import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        Book foundBook = booksService.findByTitleAndYearOfWritingAndAuthor(book);
        if(foundBook != null){
            errors.rejectValue("title", "", "Такая книга уже существует");
        }

    }
}
