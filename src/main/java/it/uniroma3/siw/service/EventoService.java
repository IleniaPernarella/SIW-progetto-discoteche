package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.repository.EventoRepository;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;
	
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
	
	
}
