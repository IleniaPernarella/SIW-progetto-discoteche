package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;


import it.uniroma3.siw.service.DiscotecaService;
 

@Controller
public class DiscotecaController {

	@Autowired
	private DiscotecaService discotecaService;
	
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private EventoService eventoSerivice;
	
	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
	    ;

	    if (userDetails != null) {
	        System.out.println(">>> Autenticato come: " + userDetails.getUsername());
	        model.addAttribute("username", userDetails.getUsername());
	        model.addAttribute("isAuthenticated", true);
	    } else {
	        model.addAttribute("isAuthenticated", false);
	    }

	    model.addAttribute("discoteche", discotecaService.getAllDiscoteche());
	    return "home";
	}

	



}
