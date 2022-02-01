package org.generation.italy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@NotEmpty(message="È necessario inserire una categoria")
	private String nome;
	
	@OneToMany(mappedBy= "categoria")
	private List<Ricetta> ricetta;
	//test modifica classe

	
	public Integer getId() {
		return id;
	}

	public List<Ricetta> getRicetta() {
		return ricetta;
	}

	public void setRicetta(List<Ricetta> ricetta) {
		this.ricetta = ricetta;
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

}
