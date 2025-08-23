package org.example.volodyanoy.LibrarySpringBootApp.controllers;

import jakarta.validation.Valid;
import org.example.volodyanoy.LibrarySpringBootApp.dao.BookDAO;
import org.example.volodyanoy.LibrarySpringBootApp.dao.PersonDAO;
import org.example.volodyanoy.LibrarySpringBootApp.dto.RegistrationDTO;
import org.example.volodyanoy.LibrarySpringBootApp.models.Account;
import org.example.volodyanoy.LibrarySpringBootApp.models.Person;
import org.example.volodyanoy.LibrarySpringBootApp.services.PeopleService;
import org.example.volodyanoy.LibrarySpringBootApp.util.AccountUpdateValidator;
import org.example.volodyanoy.LibrarySpringBootApp.util.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PersonDAO personDAO;
    private final BookDAO bookDAO;
    private final PeopleService peopleService;
    private final AccountValidator accountValidator;
    private final AccountUpdateValidator accountUpdateValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, BookDAO bookDAO, PeopleService peopleService, AccountValidator accountValidator, AccountUpdateValidator accountUpdateValidator) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
        this.peopleService = peopleService;
        this.accountValidator = accountValidator;
        this.accountUpdateValidator = accountUpdateValidator;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "withBooks", required = false, defaultValue = "false") Boolean withBooks) {
        model.addAttribute("withBooks", withBooks);

        if (withBooks)
            model.addAttribute("people", peopleService.findAllWithBooks());
        else
            model.addAttribute("people", peopleService.findAll());

        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получим одного человека по id и передадим в views
        model.addAttribute("person", peopleService.findOne(id));
        //Получим список книг, которые взял этот человек
        model.addAttribute("books", peopleService.getBooksInPersonPossession(id));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson() {
        return "redirect:/auth/registration";
    }

    @PostMapping()
    public String create() {
        return "redirect:/auth/registration";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        RegistrationDTO dto = convertToRegistrationDTO(peopleService.findOne(id));
        model.addAttribute("registrationDTO", dto);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("registrationDTO") @Valid RegistrationDTO registrationDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        accountUpdateValidator.validate(registrationDTO.getAccount(), bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }

        peopleService.update(id, registrationDTO);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }

    private RegistrationDTO convertToRegistrationDTO(Person person) {
        Account account = person.getAccount();
        return new RegistrationDTO(person, account);
    }


}
