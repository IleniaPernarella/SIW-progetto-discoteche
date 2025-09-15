package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Preferito;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.PreferitoService;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class RecensioneController {

	@Autowired
	private PreferitoService preferitoService;

	@Autowired
	private RecensioneService recensioneService;

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("/recensione/crea/{eventoId}")
	public String creaRecensione(@PathVariable Long eventoId, @AuthenticationPrincipal UserDetails userDetails, Model model) {


		//utente loggato
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();

		Preferito pref = preferitoService.getPreferitoPerEvento(utente,eventoId);


		if (pref == null) {
			model.addAttribute("errore", "Devi aggiungere l'evento ai preferiti prima di scrivere una recensione!");
			return "errore";
		}

		if (pref.getRecensione() != null) {
			model.addAttribute("errore", "Hai già recensito questo evento!");
			return "errore";
		}
		
		Recensione recensione= new Recensione();
		recensione.setPreferito(pref);
		model.addAttribute("recensione", recensione);
		
		return "recensioneForm"; 

	}
	
	// Salva la recensione
    @PostMapping("/recensione/salva")
    public String salvaRecensione(@ModelAttribute Recensione recensione,
                                  @AuthenticationPrincipal UserDetails userDetails) {
    	
    	Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
    	recensione.setUtente(utente);
    	
    	// Associa preferito dal form
        Preferito pref = preferitoService.getPreferitoById(recensione.getPreferito().getId());
        recensione.setPreferito(pref);
        
     // 🔹 Imposto anche l’evento legato al preferito
        recensione.setEvento(pref.getEvento());
        
    	recensioneService.save(recensione);
    	
    	return"redirect:/preferiti";
    }
}
