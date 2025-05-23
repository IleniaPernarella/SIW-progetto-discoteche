package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;

@Controller
public class DiscotecaController {

	@Autowired
	private DiscotecaService discotecaService;
	
	@Autowired
	private EventoService eventoSerivice;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("discoteche", discotecaService.getAllDiscoteche());
		return "home";
	}
	
	@GetMapping("/discoteca/{id}")
	public String mostraDiscoteca(@PathVariable Long id,Model model) {
		
		if(discotecaService.getDiscoteca(id)==null) {
			return "redirect:/";
		}
		
		model.addAttribute("discoteca",discotecaService.getDiscoteca(id));
		model.addAttribute("eventi",eventoSerivice.getEventiPerDiscoteca(discotecaService.getDiscoteca(id)));
		
		return "discoteca.html";
	}
}
