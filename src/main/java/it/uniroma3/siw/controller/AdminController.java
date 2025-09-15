package it.uniroma3.siw.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.DiscotecaRepository;
import it.uniroma3.siw.repository.EventoRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;


@Controller
public class AdminController {



	@Autowired
	CredentialsService credentialsService;

	@Autowired
	private DiscotecaService discotecaService;

	@Autowired
	private EventoService eventoService;


	

	@GetMapping("/admin/home")
	public String homeAdmin(Model model) {
		return "admin-home";
	}

	@GetMapping("/admin/utenti")
	public String utentiAdmin(Model model) {
		model.addAttribute("utenti",credentialsService.getAllCredentials());

		return "admin-utenti";
	}


	@PostMapping("/admin/utenti")
	public String modificaUtente(@RequestParam Long id, @RequestParam String ruolo) {
		Credentials credentials = credentialsService.getCredentials(id);
		if(credentials!=null) {
			credentials.setRole(ruolo);
			credentialsService.saveCredentials(credentials);
		}
		return "redirect:/admin/utenti";
	}

	@GetMapping("/admin/discoteche")
	public String showDiscoteche(Model model) {
		
		UserDetails userDetails= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente gestore = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		
		Iterable<Discoteca> tutteDiscoteche = discotecaService.getAllDiscoteche();
		List<Discoteca> discotecheAdmin = new ArrayList<>();
		
		for(Discoteca discoteca : tutteDiscoteche) {
			if(discoteca.getGestore()!=null && discoteca.getGestore().getId().equals(gestore.getId())) {
				discotecheAdmin.add(discoteca);
			}
			
			model.addAttribute("discotecheAdmin", discotecheAdmin);
	
		}
		return "admin-discoteche";
	}

	@PostMapping("/admin/discoteche")
	public String showDiscoteche(@RequestParam String nome, @RequestParam String indirizzo, @RequestParam String descrizione,
			@RequestParam String immagine,@AuthenticationPrincipal UserDetails userDetails) {
		
	
		Utente gestore = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		
		Discoteca discoteca = new Discoteca();
		discoteca.setNome(nome);
		discoteca.setIndirizzo(indirizzo);
		discoteca.setDescrizione(descrizione);
		discoteca.setImmagine(immagine);
		discoteca.setGestore(gestore);
		
		discotecaService.save(discoteca);
		
		return "redirect:/admin/discoteche";
	}

	@PostMapping("/admin/discoteche/delete/{id}")
    public String eliminaDiscoteca(@PathVariable Long id) {
		Discoteca discoteca = discotecaService.getDiscoteca(id);
		
		Iterable<Evento> eventi = eventoService.getEventiPerDiscoteca(discoteca);
		eventoService.eliminaEventi(eventi);
		
        discotecaService.eliminaDiscoteca(id);
        return "redirect:/admin/discoteche"; 
    }
	
	@PostMapping("/admin/eventi/delete/{id}")
    public String eliminaEvento(@PathVariable Long id) {
        Evento evento = eventoService.getEvento(id);
        eventoService.eliminaEvento(id);
        Long discotecaId= evento.getDiscoteca().getId();
        return "redirect:/admin/discoteche/" + discotecaId + "/eventi";
    }
	
	@GetMapping("/admin/discoteche/{id}/eventi")
	public String gestioneEventi(@PathVariable Long id, Model model) {
		Discoteca discoteca= discotecaService.getDiscoteca(id);
		model.addAttribute("discoteca", discoteca);
		model.addAttribute("eventi",eventoService.getEventiPerDiscoteca(discoteca));
		
		return "admin-eventi";
	}
	
	@GetMapping("/admin/discoteche/{id}/eventi/nuovo")
	public String nuovoEvento(@PathVariable Long id, Model model) {
		Discoteca discoteca= discotecaService.getDiscoteca(id);
		model.addAttribute("discoteca",discoteca);
		model.addAttribute("discotecaId", discoteca.getId());
		model.addAttribute("evento", new Evento());
		return "admin-nuovo-evento";
	}
	
	@PostMapping("/admin/discoteche/{id}/eventi")
	public String creaEvento(@PathVariable Long id,
	                         @RequestParam String nome,
	                         @RequestParam String descrizione,
	                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataOra,
	                         @RequestParam float prezzo) {
	    Discoteca discoteca = discotecaService.getDiscoteca(id);
	    Evento evento = new Evento();
	    evento.setNome(nome);
	    evento.setDescrizione(descrizione);
	    evento.setPrezzo(prezzo);
	    evento.setDataOra(dataOra);
	    evento.setDiscoteca(discoteca);
	    eventoService.save(evento);
	    
	    return "redirect:/admin/discoteche/" + id + "/eventi";
	    
	}
}
