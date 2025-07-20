package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Evento {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	@NotNull
	@Column(length = 2000)
	private String descrizione;
	private LocalDateTime dataOra;
	private float prezzo;
	
	@ManyToOne
	@JoinColumn(name="ID_discoteca")
	private Discoteca discoteca;
	
	@OneToMany(mappedBy="evento")
	private List<Preferito> preferiti;

	
	
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

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDateTime getDataOra() {
		return dataOra;
	}

	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public Discoteca getDiscoteca() {
		return discoteca;
	}

	public void setDiscoteca(Discoteca discoteca) {
		this.discoteca = discoteca;
	}

	public List<Preferito> getPreferiti() {
		return preferiti;
	}

	public void setPreferiti(List<Preferito> preferiti) {
		this.preferiti = preferiti;
	}
	
	
	
	
}
