package org.generation.italy.service;

import org.generation.italy.repository.ImmagineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImmagineService {

	@Autowired
	public ImmagineRepository repo;
}
