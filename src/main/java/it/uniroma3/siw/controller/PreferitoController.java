package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Preferito;
import it.uniroma3.siw.model.Recensione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.PreferitoRepository;
import it.uniroma3.siw.repository.RecensioneRepository;
import it.uniroma3.siw.service.PreferitoService;
import it.uniroma3.siw.service.RecensioneService;

@Controller
public class PreferitoController {


	@Autowired
	private CredentialsRepository credentialsRepository;
	@Autowired
	private PreferitoRepository preferitoRepository;
	@Autowired
	private PreferitoService preferitoService;


	@GetMapping("/preferiti")
	public String mostraPreferitiUtente(Model model, Principal principal) {

	    if(principal == null) return "redirect:/login";

	    String username = principal.getName();
	    Credentials cred = credentialsRepository.findByUsername(username).orElse(null);
	    if(cred == null) return "redirect:/";

	    Utente utente = cred.getUtente();
	    List<Preferito> preferitiUtente = preferitoRepository.findByUtente(utente);

	    Map<Long, Recensione> recensioniUtentePerEvento = new HashMap<>();
	    Map<Long, List<Recensione>> recensioniAltriPerEvento = new HashMap<>();

	    for (Preferito p : preferitiUtente) {
	        List<Recensione> tutteRecensioni = preferitoService.getRecensioniPerEvento(p);
	        if (tutteRecensioni == null) tutteRecensioni = List.of();

	        // separo recensione dell'utente loggato e degli altri
	        Recensione recensioneUtente = null;
	        List<Recensione> altri = new java.util.ArrayList<>();

	        for (Recensione r : tutteRecensioni) {
	            if (r.getUtente().equals(utente)) recensioneUtente = r;
	            else altri.add(r);
	        }

	        recensioniUtentePerEvento.put(p.getEvento().getId(), recensioneUtente);
	        recensioniAltriPerEvento.put(p.getEvento().getId(), altri);
	    }

	    model.addAttribute("preferiti", preferitiUtente);
	    model.addAttribute("recensioniUtentePerEvento", recensioniUtentePerEvento);
	    model.addAttribute("recensioniAltriPerEvento", recensioniAltriPerEvento);
	    model.addAttribute("username", username);

	    return "preferiti";
	}


	@PostMapping("/preferito/{id}/rimuovi")
	public String rimuoviEventoAiPreferiti(@PathVariable Long id, Principal principal) {

		if(principal==null) {
			return "redirect:/";
		}

		Preferito preferito = preferitoRepository.findById(id).orElse(null);


		if(preferito!=null) {
			
			
			//Per sicurezza controllo che il preferito sia dell'utente loggato
			String username = principal.getName();
			Credentials cred = credentialsRepository.findByUsername(username).orElse(null);

			if(cred!=null && cred.getUtente().equals(preferito.getUtente())) {
				preferitoRepository.delete(preferito);
			}
		}

		return "redirect:/preferiti";

	}

}
