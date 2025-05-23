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
<<<<<<< HEAD
	
	public Discoteca getDiscoteca(Long id) {
		return discotecaRepository.findById(id).get();
	}
=======
>>>>>>> bd7342ef0c8b5f015d7324f5e497c8d97f1b2be6
}
