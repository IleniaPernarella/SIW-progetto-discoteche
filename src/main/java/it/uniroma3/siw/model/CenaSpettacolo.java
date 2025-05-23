package it.uniroma3.siw.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class CenaSpettacolo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nome;
	@NotNull
	private String descrizione;
	private Date data;
	private Time orario;
	private float prezzo;
	private int postiDisponibili;
	
	@OneToMany(mappedBy = "cenaSpettacoloScelta")
    private List<Discoteca> discoteche;
	
	@ManyToOne
    @JoinColumn(name = "ID_Gestore_Creatore")
    private Utente gestoreCreatore;

	
	
	
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getOrario() {
		return orario;
	}

	public void setOrario(Time orario) {
		this.orario = orario;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public Integer getPostiDisponibili() {
		return postiDisponibili;
	}

	public void setPostiDisponibili(Integer postiDisponibili) {
		this.postiDisponibili = postiDisponibili;
	}

	public List<Discoteca> getDiscoteche() {
		return discoteche;
	}

	public void setDiscoteche(List<Discoteca> discoteche) {
		this.discoteche = discoteche;
	}

	public Utente getGestoreCreatore() {
		return gestoreCreatore;
	}

	public void setGestoreCreatore(Utente gestoreCreatore) {
		this.gestoreCreatore = gestoreCreatore;
	}
	
	
}
