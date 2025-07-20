package it.uniroma3.siw.controller;

import java.security.Principal;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;

@Controller
public class EventoController {

   
	@Autowired
	private DiscotecaService discotecaService;


	@Autowired
	private EventoService eventoSerivice;

	
	@GetMapping("/discoteca/{id}")
	public String mostraDiscoteca(@PathVariable Long id,Model model,Principal principal) {
		
		if (principal != null) {
	        System.out.println("Utente loggato: " + principal.getName());
	        model.addAttribute("username", principal.getName());
	    }
		
		if(discotecaService.getDiscoteca(id)==null) {
			return "redirect:/";
		}
		
		model.addAttribute("discoteca",discotecaService.getDiscoteca(id));
		model.addAttribute("eventi",eventoSerivice.getEventiPerDiscoteca(discotecaService.getDiscoteca(id)));
		
		return "discoteca.html";
	}
	
	
	@PostMapping("/evento/{id}/preferito")
	public String aggiungiEventoAiPreferiti(@PathVariable Long id, Principal principal) {
		
		if(principal!=null) {
			String username= principal.getName();
			eventoSerivice.aggiungiEventoAiPreferiti(id, username);
		}
		
		Evento evento = eventoSerivice.getEvento(id);
		if(evento!=null && evento.getDiscoteca()!=null) {
			return "redirect:/discoteca/" + eventoSerivice.getEvento(id).getDiscoteca().getId();
		}
		
		return "redirect:/";
	}
}



