package org.generation.italy.model;

import java.util.List;

public class IngredienteList {

	private List<Ingrediente> ingredienti;
	
	public void addIngrediente(Ingrediente ingrediente) {
		this.ingredienti.add(ingrediente);
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	
}
