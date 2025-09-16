package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {

	@Autowired
	private RecensioneRepository recensioneRepository;
	
	@Autowired
    private DiscotecaService discotecaService;

    @Autowired
    private EventoService eventoService;

	public void save(Recensione recensione) {
		recensioneRepository.save(recensione);
	}
	
	public Double calcolaMediaPerEvento(Evento evento) {
		Iterable<Recensione> recensioni = recensioneRepository.findAll();
		
		double somma=0;
		double count=0;
		
		for(Recensione r: recensioni) {
			if(r.getEvento()!=null && r.getEvento().getId().equals(evento.getId())) {
				somma+=r.getValutazione();
				count++;
			}
		}
		
		if(count==0)
			return null;
		
		return somma/count;
	}
	
	 
    public List<Recensione> getRecensionePerEvento(Evento evento) {
        List<Recensione> recensioniPerVento = new ArrayList<>();
        for (Recensione r : recensioneRepository.findAll()) {
            if (r.getEvento() != null && r.getEvento().getId().equals(evento.getId())) {
            	recensioniPerVento.add(r);
            }
        }
        return recensioniPerVento; 
    }
    
    public List<Recensione> getRecensioniPerGestore(Utente gestore){
    	
    	List<Recensione> recensioniPerGestore = new ArrayList<>();
    	
    	List<Discoteca> mieDiscoteche = discotecaService.getDiscotecheByGestore(gestore);
    	
    	
    	//per ogni mia discoteca recupero eventi
    	for(Discoteca d: mieDiscoteche) {
    		List<Evento> eventi = d.getEventi();
    		//degli eventi recupero le recensioni
    		for(Evento e : eventi) {
    			recensioniPerGestore.addAll(getRecensionePerEvento(e));
    		}
    	}
    	return recensioniPerGestore;
    }
    
    @Transactional
    public void eliminaRecensione(Long id) {
          Recensione recensione = recensioneRepository.findById(id).orElseThrow();
          
          if(recensione.getEvento()!=null) {
        	  recensione.getEvento().getRecensioni().remove(recensione);
        	  recensione.setEvento(null);
          }
          
          if(recensione.getPreferito()!=null) {
        	  recensione.getPreferito().setRecensione(null);
        	  recensione.setPreferito(null);
          }
          
          recensioneRepository.delete(recensione);
    }
}

