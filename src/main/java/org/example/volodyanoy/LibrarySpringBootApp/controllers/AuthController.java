package org.example.volodyanoy.LibrarySpringBootApp.controllers;

import jakarta.validation.Valid;
import org.example.volodyanoy.LibrarySpringBootApp.dto.RegistrationDTO;
import org.example.volodyanoy.LibrarySpringBootApp.services.RegistrationService;
import org.example.volodyanoy.LibrarySpringBootApp.util.AccountValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AccountValidator accountValidator;
    private final RegistrationService registrationService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(AccountValidator accountValidator, RegistrationService registrationService) {
        this.accountValidator = accountValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("registrationDTO") RegistrationDTO registrationDTO) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("registrationDTO") @Valid RegistrationDTO registrationDTO, BindingResult bindingResult) {
        accountValidator.validate(registrationDTO.getAccount(), bindingResult);

        if (bindingResult.hasErrors()) {
            logger.info("Ошибка данных при регистрации пользователя {}", registrationDTO.getAccount());
            return "/auth/registration";
        }
        registrationService.register(registrationDTO);
        logger.info("Успешная регистрация пользователя {}", registrationDTO.getAccount());

        return "redirect:/auth/login";
    }

}
