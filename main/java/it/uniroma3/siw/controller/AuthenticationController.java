package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;
    
    @Autowired
    private EventoService eventoService;

    @Autowired
    private DiscotecaService discotecaService;

    // 👤 Form registrazione
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
    	model.addAttribute("utente", new Utente() );
        model.addAttribute("credentials", new Credentials());
        return "register";
    }

    // 💾 Invio form registrazione
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult bindingResult,
                               @RequestParam("confermaPassword") String confermaPassword,
                               Model model) {

        // 🔐 Validazione password
        if (!credentials.getPassword().equals(confermaPassword)) {
            bindingResult.rejectValue("password", "error.password", "Le password non corrispondono");
        }

 
        // 🧾 Salvataggio
        if (!bindingResult.hasErrors()) {
            credentialsService.saveCredentials(credentials); // salva anche utente se Cascade.ALL
            return "redirect:/login";
        }

        return "register";
    }

    // 🔑 Form login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
    	model.addAttribute("numEventi", eventoService.countEventi());
    	model.addAttribute("numDiscoteche", discotecaService.countDiscoteche());
        return "admin/dashboard";
    }
    
    // 🔄 Redirect dopo login
    @GetMapping("/success")
    public String loginSuccessRedirect(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	 
    	if (credentials == null) {
    		 return "redirect:/login";
    	 }
    	

        if (credentials.getRole().equalsIgnoreCase(Credentials.ADMIN_ROLE)) {
            return "redirect:/admin/dashboard";
        }

        return "redirect:/";
    }
}
