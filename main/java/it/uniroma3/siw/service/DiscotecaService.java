package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.repository.DiscotecaRepository;

@Service
public class DiscotecaService {

	@Autowired
	private DiscotecaRepository discotecaRepository;
	
	
	public Iterable<Discoteca> getAllDiscoteche(){
		return discotecaRepository.findAll();
	}
	public long countDiscoteche() {
	    return discotecaRepository.count();
	}
	
	public Discoteca getDiscoteca(Long id) {
		return discotecaRepository.findById(id).get();
	}
	
	public Discoteca salvaDiscoteca(Discoteca discoteca) {
	    return discotecaRepository.save(discoteca);
	}

	public void cancellaDiscoteca(Long id) {
	    discotecaRepository.deleteById(id);
	}

	public Discoteca modificaDiscoteca(Long id, Discoteca nuovaDiscoteca) {
	    Discoteca esistente = getDiscoteca(id);
	    if (esistente != null) {
	        esistente.setNome(nuovaDiscoteca.getNome());
	        esistente.setIndirizzo(nuovaDiscoteca.getIndirizzo());
	        esistente.setDescrizione(nuovaDiscoteca.getDescrizione());
	        esistente.setImmagine(nuovaDiscoteca.getImmagine());
	        esistente.setGestore(nuovaDiscoteca.getGestore());
	        return discotecaRepository.save(esistente);
	    }
	    return null;
	}
	public Discoteca getDiscotecaById(Long id) {
	    return discotecaRepository.findById(id).orElse(null);
	}



}
