package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.model.Preferito;
import it.uniroma3.siw.model.Utente;

public interface PreferitoRepository extends CrudRepository<Preferito,Long>{

	 boolean existsByUtenteAndEvento(Utente utente, Evento evento);

	List<Preferito> findByUtente(Utente utente);
}
