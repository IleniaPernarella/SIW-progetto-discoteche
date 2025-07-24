package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Discoteca;


public interface DiscotecaRepository extends CrudRepository<Discoteca,Long>{
	Optional<Discoteca> findByNome(String nome);
}
