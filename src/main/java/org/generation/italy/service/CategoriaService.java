package org.generation.italy.service;

import org.generation.italy.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	public CategoriaRepository repo;
}
