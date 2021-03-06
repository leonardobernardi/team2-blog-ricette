package org.generation.italy.service;


import org.generation.italy.model.Immagine;
import org.generation.italy.repository.ImmagineRepository;
import org.generation.italy.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImmagineService {

	@Autowired
	public ImmagineRepository repo;
	

	@Autowired
	public RicettaRepository ricRepo;
	
	public Immagine getById(Integer imgId) {
		return repo.getById(imgId);
	}
}
