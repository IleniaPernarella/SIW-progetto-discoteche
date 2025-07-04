package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    private UtenteService userService;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("credentials", new Credentials());
        return "register";
    }

    @PostMapping("/register")
public String registerUser(@Valid @ModelAttribute("utente") Utente user,
                           BindingResult userBinding,
                           @Valid @ModelAttribute("credentials") Credentials credentials,
                           BindingResult credentialsBinding,
                           @ModelAttribute("confermaPassword") String confermaPassword,
                           Model model) {
    
    if (!credentials.getPassword().equals(confermaPassword)) {
        credentialsBinding.rejectValue("password", "error.password", "Le password non corrispondono");
    }

    if (!userBinding.hasErrors() && !credentialsBinding.hasErrors()) {
        userService.saveUser(user);
        credentials.setUtente(user);
        credentialsService.saveCredentials(credentials);
        return "redirect:/login";
    }
    return "register";
}


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/success")
    public String loginSuccessRedirect() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/";
    }
}