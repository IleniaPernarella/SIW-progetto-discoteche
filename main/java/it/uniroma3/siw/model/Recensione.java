package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Recensione {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String testo;
	private int valutazione;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_Utente")
    private Utente utente;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_Preferito", unique = true)
    private Preferito preferito;

    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public int getValutazione() {
		return valutazione;
	}

	public void setValutazione(int valutazione) {
		this.valutazione = valutazione;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Preferito getPreferito() {
		return preferito;
	}

	public void setPreferito(Preferito preferito) {
		this.preferito = preferito;
	}
    
    

}
