package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {

	@Autowired
	private RecensioneRepository recensioneRepository;

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
	
	public List<Recensione> getRecensionePerEvento(Evento evento){
		return recensioneRepository.findByEvento(evento);
	}
}

