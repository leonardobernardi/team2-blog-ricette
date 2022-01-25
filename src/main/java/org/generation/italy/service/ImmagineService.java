package org.generation.italy.service;

import java.io.IOException;
import java.util.List;

import org.generation.italy.model.Immagine;
import org.generation.italy.model.ImmagineForm;
import org.generation.italy.repository.ImmagineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImmagineService {

	@Autowired
	public ImmagineRepository repo;
	
	public Immagine create(ImmagineForm newImmagine) throws IOException{
		Immagine immagine = new Immagine();
		if(newImmagine.getContent() != null) {
			byte[] contentSerialized = newImmagine.getContent().getBytes();
			immagine.setContent(contentSerialized);
		}
		
		return repo.save(immagine);
	};
	
	public List<Immagine> findAll(){
		return repo.findAll();
	};
	
	public Immagine getById(Integer id) {
		return repo.getById(id);
	}
}
