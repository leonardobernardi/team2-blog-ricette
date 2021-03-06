package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Email;
import org.generation.italy.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	public EmailRepository repo;

	public List<Email> findIsBan() {
		List<Email> lista = repo.findByIsBanned(true);
		return lista;
	}


	public Email revertBan(Integer id) {
		Email email;
		email=repo.getById(id);
		if (email.getIsBanned()==true) {
			email.setIsBanned(false);
			}else email.setIsBanned(true);
		return repo.save(email);
	}

}
