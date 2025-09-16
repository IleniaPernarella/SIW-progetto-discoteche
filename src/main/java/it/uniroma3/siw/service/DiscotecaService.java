package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.DiscotecaRepository;

@Service
public class DiscotecaService {

	@Autowired
	private DiscotecaRepository discotecaRepository;
	
	public Iterable<Discoteca> getAllDiscoteche(){
		return discotecaRepository.findAll();
	}
	
	public List<Discoteca> getDiscotecheByGestore(Utente gestore){
		List<Discoteca> discotecheByGestore = new ArrayList<>();
		for(Discoteca d : discotecaRepository.findAll()) {
			if(d.getGestore()!=null && d.getGestore().getId().equals(gestore.getId())) {
				discotecheByGestore.add(d);
			}
		}
		return discotecheByGestore;
	}

	
	public Discoteca getDiscoteca(Long id) {
		return discotecaRepository.findById(id).get();
	}

	public Discoteca save(Discoteca discoteca) {
		return discotecaRepository.save(discoteca);
	}
	
	public void eliminaDiscoteca(Long id) {
        discotecaRepository.deleteById(id);
    }
}
