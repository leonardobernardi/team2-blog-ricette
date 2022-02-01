package org.generation.italy.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.generation.italy.model.Ricetta;
import org.generation.italy.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/ricetta")
public class RicettaRestController {
	
	@Autowired
	private RicettaService service;
	
	@GetMapping
	public List<Ricetta> all(){
		return service.findAllSortedByRecent();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Ricetta> one(@PathVariable Integer id){
		Ricetta ricetta = null;
		try {
			ricetta = service.getById(id);
			return new ResponseEntity<Ricetta>(ricetta, HttpStatus.OK);
		} catch (NoSuchElementException nse) {
			// TODO Auto-generated catch block
			return new ResponseEntity<Ricetta>(ricetta, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Integer> update(@PathVariable Integer id){
		try {
			service.miPiace(service.getById(id));
			return new ResponseEntity<Integer>(service.getById(id).getMiPiace(), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return new ResponseEntity<Integer>(service.getById(id).getMiPiace(), HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Integer>(service.getById(id).getMiPiace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
