package org.generation.italy.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Ricetta {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotEmpty
	private String titolo;
	
	@NotNull
	@NotEmpty
	private String tempoDiPreparazione;
	
	@NotNull
	@Min(value=1)
	@Max(value=3)
	private Integer livelloDiDifficolta;

	@NotNull
	@NotEmpty
	@Column(length=250)
	private String descrizione;
	
	@NotNull
	@NotEmpty
	@Lob
	private String testoDellaRicetta;
	
	private LocalDateTime dataDiCreazione;
	
	private Integer visualizzazioni;	
	
	private Integer miPiace;
	
	
	@ManyToOne
	private Categoria categoria;
	
	@OneToMany(mappedBy="ricetta")
	private List<Immagine> immagini;
	
	
	@OneToMany(mappedBy = "ricetta", cascade = {CascadeType.ALL})
	private List<Ingrediente> ingrediente;
	
	@OneToMany(mappedBy = "ricetta")
	private List<Commento> commenti;
	
	private Boolean isVegan;
	private Boolean isVegetarian;
	
//	// -- veg -- ret
//	public boolean isVegan() {
//
//				for (Ingrediente i : getIngrediente()) {
//					if (!i.getIsVegan()) {
//						return false;
//					}
//				}
//				return true;
//
//	}
//	
//	public boolean isVegetarian() {		
//		if(ricetta != null) {	
//			if(ricetta.getIngrediente().size()>0) {
//				for(Ingrediente i : ricetta.getIngrediente()) {
//					if(!i.getIsVegetarian()) {
//						return false;
//					}
//				}
//				return true;
//			}
//			
//		} 
//			return false;
//		
//	}
	
	

	public Integer getMiPiace() {
		return miPiace;
	}

	public void setMiPiace(Integer miPiace) {
		this.miPiace = miPiace;
	}

	public List<Immagine> getImmagini() {
		return immagini;
	}

	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}
	
	

//	 delete - now a method
	public Boolean getIsVegan() {
		return isVegan;
	}

	public void setIsVegan(Boolean isVegan) {
		this.isVegan = isVegan;
	}

	public Boolean getIsVegetarian() {
		return isVegetarian;
	}

	public void setIsVegetarian(Boolean isVegetarian) {
		this.isVegetarian = isVegetarian;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTempoDiPreparazione() {
		return tempoDiPreparazione;
	}

	public void setTempoDiPreparazione(String tempoDiPreparazione) {
		this.tempoDiPreparazione = tempoDiPreparazione;
	}

	public Integer getLivelloDiDifficolta() {
		return livelloDiDifficolta;
	}

	public void setLivelloDiDifficolta(Integer livelloDiDifficolta) {
		this.livelloDiDifficolta = livelloDiDifficolta;
	}

	public String getTestoDellaRicetta() {
		return testoDellaRicetta;
	}

	public void setTestoDellaRicetta(String testoDellaRicetta) {
		this.testoDellaRicetta = testoDellaRicetta;
	}

	public LocalDateTime getDataDiCreazione() {
		return dataDiCreazione;
	}

	public void setDataDiCreazione(LocalDateTime dataDiCreazione) {
		this.dataDiCreazione = dataDiCreazione;
	}

	public Integer getVisualizzazioni() {
		return visualizzazioni;
	}

	public void setVisualizzazioni(Integer visualizzazioni) {
		this.visualizzazioni = visualizzazioni;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Ingrediente> getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(List<Ingrediente> ingrediente) {
		this.ingrediente = ingrediente;
	}


	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
	
}