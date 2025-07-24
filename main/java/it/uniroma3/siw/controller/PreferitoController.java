package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Preferito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.PreferitoRepository;

@Controller
public class PreferitoController {


	@Autowired
	private CredentialsRepository credentialsRepository;
	@Autowired
	private PreferitoRepository preferitoRepository;


	@GetMapping("/preferiti")
	public String mostraPreferitiUtente(Model model, Principal principal) {

		if(principal==null) {
			return "redirect:/login";
		}

		String username = principal.getName();
		Credentials cred = credentialsRepository.findByUsername(username).orElse(null);

		if(cred==null) {
			return "redirect:/";
		}

		Utente utente = cred.getUtente();
		List<Preferito> preferitiUtente = preferitoRepository.findByUtente(utente);


		model.addAttribute("preferiti", preferitiUtente);
		model.addAttribute("username", username);

		return "utente/preferiti";
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
