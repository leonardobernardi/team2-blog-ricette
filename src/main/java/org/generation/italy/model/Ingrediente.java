package org.generation.italy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	@NotEmpty
	private String quantita;
	
	@ManyToOne
	private Ricetta ricetta;
	
	public Ricetta getRicetta() {
		return ricetta;
	}
	public void setRicetta(Ricetta ricetta) {
		this.ricetta = ricetta;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	private Boolean isVegan;
	private Boolean isVegetarian;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
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
	
	
}
