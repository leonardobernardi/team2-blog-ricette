package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Categoria;
import org.generation.italy.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	public CategoriaRepository repo;
	
	public Categoria getById(Integer id) {
		return repo.getById(id);
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
}
