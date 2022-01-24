package org.generation.italy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.generation.italy.model.IngredientiRicetta;
import org.generation.italy.model.Ricetta;
import org.generation.italy.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RicettaService {
	
	@Autowired
	private RicettaRepository repo;
	
	//Create
	public Ricetta create(Ricetta ricetta) {
		ricetta.setDataDiCreazione(LocalDateTime.now());
		ricetta.setVisualizzazioni(0);
		ricetta.setIsVegan(isVegan(ricetta));
		ricetta.setIsVegetarian(isVegetarian(ricetta));
		return repo.save(ricetta);
	}
	
	//Read
	public List<Ricetta> findAllSortedByRecent(){
		return repo.findAll(Sort.by("dataDiCreazione"));
	}
	
	public Ricetta getById(Integer id) {
		return repo.getById(id);
	}
	
	public boolean isVegan(Ricetta ricetta) {
		if(ricetta != null) {
			for(IngredientiRicetta i : ricetta.getIngredienti()) {
				if(!i.getIngrediente().getIsVegan()) {
					return false;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isVegetarian(Ricetta ricetta) {		
		if(ricetta != null) {			
			for(IngredientiRicetta i : ricetta.getIngredienti()) {
				if(!i.getIngrediente().getIsVegetarian()) {
					return false;
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	@SuppressWarnings("null")
	public List<Ricetta> findFiveMostRecent(){
		List<Ricetta> lista = repo.findAll(Sort.by("dataDiCreazione"));
		List<Ricetta> piuRecenti = null;
		int i = 0;
		while(i < 6 && i < lista.size()) {
			piuRecenti.add(lista.get(i));
			i++;
		}
		return piuRecenti;
			
	}
	
	//Update
	public Ricetta update(Ricetta ricetta) {
		LocalDateTime dataDiCreazione = repo.getById(ricetta.getId()).getDataDiCreazione();
		Integer visualizzazioni = repo.getById(ricetta.getId()).getVisualizzazioni();
		ricetta.setDataDiCreazione(dataDiCreazione);
		ricetta.setVisualizzazioni(visualizzazioni);
		ricetta.setIsVegan(isVegan(ricetta));
		ricetta.setIsVegetarian(isVegetarian(ricetta));
		return repo.save(ricetta);
	}
	
	//Delete
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}
	
	

}
