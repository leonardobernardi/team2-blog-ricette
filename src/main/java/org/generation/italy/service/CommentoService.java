package org.generation.italy.service;

import org.generation.italy.repository.CommentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentoService {

	@Autowired
	public CommentoRepository repo;
}
