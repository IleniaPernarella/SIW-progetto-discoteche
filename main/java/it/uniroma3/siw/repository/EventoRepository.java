package it.uniroma3.siw.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.model.Evento;

public interface EventoRepository extends CrudRepository<Evento,Long>{
	List<Evento> findByDiscoteca_Id(Long discotecaId);
	List<Evento> findByDataOraBetween(LocalDateTime start, LocalDateTime end);
	

}
