package it.uniroma3.siw.model;

import jakarta.validation.constraints.*;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Utente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	@NotNull
	private String cognome;
	@NotNull
	private String email;
	
	
	
	
	@OneToMany(mappedBy="gestore")
	private List<Discoteca> discotecheGestite;
	
	@OneToMany(mappedBy="utente")
	private List<Preferito> preferiti;
	
	@OneToMany(mappedBy="utente")
	private List<Recensione> recensioni;
	
	@OneToMany(mappedBy="gestoreCreatore")
	private List<CenaSpettacolo> ceneSpettacoloCreate;

	@OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    private Credentials credentials;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public List<Discoteca> getDiscotecheGestite() {
		return discotecheGestite;
	}

	public void setDiscotecheGestite(List<Discoteca> discotecheGestite) {
		this.discotecheGestite = discotecheGestite;
	}

	public List<Preferito> getPreferiti() {
		return preferiti;
	}

	public void setPreferiti(List<Preferito> preferiti) {
		this.preferiti = preferiti;
	}

	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}

	public List<CenaSpettacolo> getCeneSpettacoloCreate() {
		return ceneSpettacoloCreate;
	}

	public void setCeneSpettacoloCreate(List<CenaSpettacolo> ceneSpettacoloCreate) {
		this.ceneSpettacoloCreate = ceneSpettacoloCreate;
	}
	
	
	
	
	
}