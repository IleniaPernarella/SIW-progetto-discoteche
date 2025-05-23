package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.DiscotecaService;

@Controller
public class DiscotecaController {

	@Autowired
	private DiscotecaService discotecaService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("discoteche", discotecaService.getAllDiscoteche());
		return "home";
	}
}
