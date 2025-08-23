package org.example.volodyanoy.LibrarySpringBootApp.controllers;

import jakarta.validation.Valid;
import org.example.volodyanoy.LibrarySpringBootApp.dao.BookDAO;
import org.example.volodyanoy.LibrarySpringBootApp.dao.PersonDAO;
import org.example.volodyanoy.LibrarySpringBootApp.models.Book;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.services.BooksService;
import org.example.volodyanoy.LibrarySpringBootApp.services.PeopleService;
import org.example.volodyanoy.LibrarySpringBootApp.util.BookValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/books")
public class BooksController {
    private static final Logger log = LoggerFactory.getLogger(BooksController.class);
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;
    private final BooksService booksService;
    private final PeopleService peopleService;
    private static final Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO, BooksService booksService, PeopleService peopleService) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    //sort_by_year
    // page и books_per_page
    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") Boolean sortByYear,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage) {
        logger.info("Параметры GET запроса sort_by_year= {}, page= {}, bookf_per_page= {}", sortByYear, page, booksPerPage);
        if (page != null && booksPerPage != null) {
            if (sortByYear) {
                model.addAttribute("books", booksService.findAllAndSortByYearOfWritingAndPagination(page, booksPerPage));
            } else {
                model.addAttribute("books", booksService.findAllAndPagination(page, booksPerPage));
            }
        } else if (sortByYear) {
            model.addAttribute("books", booksService.findAllAndSortByYearOfWriting());
        } else {
            model.addAttribute("books", booksService.findAll());
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        //Получим одну книгу по id и передадим в views
        model.addAttribute("book", booksService.findOne(id));
        //Получим владельца книги
        model.addAttribute("personOwnerOfBook", booksService.getOwnerOfBook(id));
        //Получим всех людей для выпадающего списка
        model.addAttribute("people", peopleService.findAll());

        logger.debug("book {}, personOwnerOfBook {}, size of people list {}", model.asMap().get("book"),
                model.asMap().get("personOwnerOfBook"),
                ((Collection<?>) model.asMap().get("people")).size());

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        booksService.save(book);
        return "redirect:/books";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assignBook(id, person);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam(value = "query", required = false) String searchQuery) {
        boolean searchFlag = false;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            model.addAttribute("books", booksService.findBooksByTitle(searchQuery));
            searchFlag = true;
        }
        model.addAttribute("searchFlag", searchFlag);

        return "/books/search";
    }


}
