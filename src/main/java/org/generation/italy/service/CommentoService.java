package org.generation.italy.service;

import java.util.Collections;
import java.util.List;



//import java.time.LocalDateTime;

import org.generation.italy.model.Commento;
import org.generation.italy.model.Email;
import org.generation.italy.model.Ricetta;
//import org.generation.italy.model.Ingrediente;
//import org.generation.italy.model.Ricetta;
import org.generation.italy.repository.CommentoRepository;
import org.generation.italy.repository.EmailRepository;
import org.generation.italy.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CommentoService {

	@Autowired
	public CommentoRepository repo;
	
	@Autowired
	public EmailRepository emailRepo;
	
	@Autowired
	public RicettaRepository ricettaRepo;
	
	public Commento create(Commento commento, Integer id) {
		String emailForm=commento.getEmail().getEmail();
		
		
		if (emailRepo.findByEmailContaining(emailForm)!=null) {
			commento.setEmail(emailRepo.findByEmailContaining(emailForm));
		}else {
		Email nuovaEmail = new Email();
		nuovaEmail.setEmail(emailForm);
		nuovaEmail.setIsBanned(false);
		emailRepo.save(nuovaEmail);
		commento.setEmail(nuovaEmail);
		}
		Ricetta ricetta = ricettaRepo.getById(id);
		Commento nuovoCommento = new Commento();
		nuovoCommento.setEmail(commento.getEmail());
		nuovoCommento.setNome(commento.getNome());
		nuovoCommento.setTesto(commento.getTesto());
		nuovoCommento.setRicetta(ricetta);
		return repo.save(nuovoCommento);
	}
	
	public List<Commento> findAll(){
		List<Commento> list = repo.findAll(Sort.by(Direction.ASC,"ricetta"));
		Collections.reverse(list);
		return list;
	}

	
	public void deleteCommentoById(Integer id) {
		repo.deleteById(id);
	}



}
