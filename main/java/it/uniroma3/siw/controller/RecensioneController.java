package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Preferito;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.PreferitoRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class RecensioneController {
	 @Autowired
	    private RecensioneService recensioneService;

	    @Autowired
	    private CredentialsService credentialsService;

	    @Autowired
	    private PreferitoRepository preferitoRepository;

	    @GetMapping("/preferito/{id}/recensione")
	    public String mostraFormRecensione(@PathVariable Long id, Model model) {
	        Preferito preferito = preferitoRepository.findById(id).orElse(null);
	        if (preferito == null) return "redirect:/preferiti";

	        model.addAttribute("recensione", new Recensione());
	        model.addAttribute("preferito", preferito);
	        return "utente/formRecensione";
	    }

	    @PostMapping("/preferito/{id}/recensione")
	    public String salvaRecensione(@PathVariable Long id,
	                                  @ModelAttribute Recensione recensione,
	                                  Principal principal) {

	        Preferito preferito = preferitoRepository.findById(id).orElse(null);
	        if (preferito == null) return "redirect:/preferiti";

	        Credentials cred = credentialsService.getCredentials(principal.getName());

	        recensione.setUtente(cred.getUtente());
	        recensione.setPreferito(preferito);
	        recensioneService.salvaRecensione(recensione);

	        return "redirect:/preferiti";
	    }
	
	    @GetMapping("/admin/recensioni")
	    public String mostraRecensioniAdmin(Model model) {
	        List<Recensione> lista = recensioneService.getAll();
	        
	        System.out.println("Numero recensioni trovate: " + lista.size());

	        for (Recensione r : lista) {
	            System.out.println("→ Recensione: " + r.getTesto());
	            System.out.println("→ Evento: " + r.getPreferito().getEvento().getNome());
	            System.out.println("→ Discoteca: " + r.getPreferito().getEvento().getDiscoteca().getNome());
	            System.out.println("→ Utente: " + r.getUtente().getNome());
	        }

	        model.addAttribute("recensioni", lista);
	        return "admin/listaRecensioni";
	    } 
	    
	    @GetMapping("/utente/recensioni")
	    public String mostraRecensioniUtente(Principal principal, Model model) {
	        Credentials cred = credentialsService.getCredentials(principal.getName());
	        model.addAttribute("recensioni", recensioneService.getRecensioniPerUtente(cred.getUtente()));
	        return "utente/recensioni";
	    }
	}


