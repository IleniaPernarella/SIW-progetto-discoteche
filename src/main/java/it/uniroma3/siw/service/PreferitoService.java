package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Preferito;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.PreferitoRepository;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class PreferitoService {

	@Autowired
	private PreferitoRepository preferitoRepository;
	@Autowired
	private RecensioneRepository recensioneRepository;


	
	public void save(Preferito preferito) {
        preferitoRepository.save(preferito);
    }
	
	//metodo per ottenere il preferito di un utente per un evento
    public Preferito getPreferitoPerEvento(Utente utente, Long eventoId) {
    	
    	if(utente.getPreferiti()==null) return null;
    	
    	for(Preferito p : utente.getPreferiti()) {
    		if(p.getEvento().getId().equals(eventoId)) {
    			return p;
    		}
    	}
    	return null;
    }
    
    public Preferito getPreferitoById(Long id) {
        Optional<Preferito> pref = preferitoRepository.findById(id);
        return pref.orElse(null); // restituisce null se non trovato
    }
    
    public List<Recensione> getRecensioniPerEvento(Preferito preferito) {
        // prende tutte le recensioni dell'evento del preferito
        return recensioneRepository.findByEvento(preferito.getEvento());
    }

}
