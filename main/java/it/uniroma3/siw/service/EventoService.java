package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.model.Preferito;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.EventoRepository;
import it.uniroma3.siw.repository.PreferitoRepository;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class EventoService {

    

	@Autowired
	private EventoRepository eventoRepository;
	@Autowired
	private CredentialsRepository credentialsRepository;
	@Autowired
	private PreferitoRepository preferitoRepository;


	
	public Iterable<Evento> getEventiPerDiscoteca(Discoteca discoteca){
		
		Iterable<Evento> getAllEventi=eventoRepository.findAll();
		List<Evento> eventiPerDiscoteca= new ArrayList<>();
		
		for(Evento evento: getAllEventi) {
			if(evento.getDiscoteca() != null &&  evento.getDiscoteca().getId().equals(discoteca.getId())) {
				eventiPerDiscoteca.add(evento);
			}
		}
		return eventiPerDiscoteca;
	}
	
	public long countEventi() {
	    return eventoRepository.count();
	}
	
	public void aggiungiEventoAiPreferiti(Long eventoId, String username) {
		
		Optional<Evento> eventoOp = eventoRepository.findById(eventoId);
		Optional<Credentials> utenteOp = credentialsRepository.findByUsername(username);
		
		if(eventoOp.isPresent() && utenteOp.isPresent()) {
			Utente utente = utenteOp.get().getUtente();
			Evento evento = eventoOp.get();
			
			if(!preferitoRepository.existsByUtenteAndEvento(utente, evento)) {
				Preferito preferito = new Preferito();
				preferito.setUtente(utente);
				preferito.setEvento(evento);
				preferito.setDataAggiunta(LocalDate.now()); 
				preferitoRepository.save(preferito);
			}
		}
		
	}
	
	
	
	public Evento getEvento(Long id) {
	    return eventoRepository.findById(id).orElse(null);
	}
	
	public Evento salvaEvento(Evento evento) {
	    return eventoRepository.save(evento);
	}

	public void cancellaEvento(Long id) {
	    eventoRepository.deleteById(id);
	}

	public Evento modificaEvento(Long id, Evento nuovoEvento) {
	    Evento esistente = getEvento(id);
	    if (esistente != null) {
	        esistente.setNome(nuovoEvento.getNome());
	        esistente.setDescrizione(nuovoEvento.getDescrizione());
	        esistente.setDataOra(nuovoEvento.getDataOra());
	        esistente.setPrezzo(nuovoEvento.getPrezzo());
	        esistente.setDiscoteca(nuovoEvento.getDiscoteca());
	        return eventoRepository.save(esistente);
	    }
	    return null;
	}
}
