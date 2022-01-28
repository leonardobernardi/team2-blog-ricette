package org.generation.italy.service;

import java.util.List;
import org.generation.italy.model.Ingrediente;
import org.generation.italy.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service

public class IngredienteService {
	
	@Autowired
	private IngredienteRepository repository;
	
	
	
	//create
	public Ingrediente create(Ingrediente ingrediente) {
		return repository.save(ingrediente);
	}
	
	//read
	public List<Ingrediente> findAllSortedByName(){
		return repository.findAll(Sort.by("nome"));
		
	}
	
	public List<Ingrediente> findByRicettaId(Integer id){
		return repository.findByRicetta(id);
	}
	
	public Ingrediente getById(Integer id) {
		return repository.getById(id);
	}
	
	//update
	public Ingrediente update(Ingrediente ingrediente) {
		return repository.save(ingrediente);
	}
	
	//delete
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

}
