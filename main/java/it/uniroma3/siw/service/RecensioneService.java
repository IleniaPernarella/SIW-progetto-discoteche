package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {
	@Autowired
    private RecensioneRepository recensioneRepository;

    public Recensione getRecensione(Long id) {
        return recensioneRepository.findById(id).orElse(null);
    }

    public Recensione salvaRecensione(Recensione recensione) {
        return recensioneRepository.save(recensione);
    }

    public void cancellaRecensione(Long id) {
        recensioneRepository.deleteById(id);
    }

    public List<Recensione> getRecensioniPerUtente(Utente utente) {
        return recensioneRepository.findByUtente(utente);
    }
    
    public List<Recensione> getAll() {
        return (List<Recensione>) recensioneRepository.findAll();
    }


}


