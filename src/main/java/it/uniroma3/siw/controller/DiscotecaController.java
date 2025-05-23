package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;
=======

import it.uniroma3.siw.service.DiscotecaService;
>>>>>>> bd7342ef0c8b5f015d7324f5e497c8d97f1b2be6

@Controller
public class DiscotecaController {

	@Autowired
	private DiscotecaService discotecaService;
	
<<<<<<< HEAD
	@Autowired
	private EventoService eventoSerivice;
	
=======
>>>>>>> bd7342ef0c8b5f015d7324f5e497c8d97f1b2be6
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("discoteche", discotecaService.getAllDiscoteche());
		return "home";
	}
<<<<<<< HEAD
	
	@GetMapping("/discoteca/{id}")
	public String mostraDiscoteca(@PathVariable Long id,Model model) {
		
		if(discotecaService.getDiscoteca(id)==null) {
			return "redirect:/";
		}
		
		model.addAttribute("discoteca",discotecaService.getDiscoteca(id));
		model.addAttribute("eventi",eventoSerivice.getEventiPerDiscoteca(discotecaService.getDiscoteca(id)));
		
		return "discoteca.html";
	}
=======
>>>>>>> bd7342ef0c8b5f015d7324f5e497c8d97f1b2be6
}
