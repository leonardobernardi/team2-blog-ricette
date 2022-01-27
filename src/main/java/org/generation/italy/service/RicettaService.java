package org.generation.italy.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.generation.italy.utilities.*;
import org.generation.italy.model.Immagine;
import org.generation.italy.model.ImmagineForm;
import org.generation.italy.model.ImmagineList;
import org.generation.italy.model.Ingrediente;
import org.generation.italy.model.IngredienteList;
import org.generation.italy.model.Ricetta;
import org.generation.italy.repository.ImmagineRepository;
import org.generation.italy.repository.IngredienteRepository;
import org.generation.italy.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class RicettaService {

	@Autowired
	private RicettaRepository repo;

	@Autowired
	private IngredienteRepository ingredienteRepo;
	
	@Autowired
	private ImmagineRepository imgRepo;
	
	// Create
	public Ricetta create(Ricetta ricetta, IngredienteList ingredienteList, ImmagineList immagineList) throws IOException {
		ricetta.setDataDiCreazione(LocalDateTime.now());
		ricetta.setVisualizzazioni(0);
		ricetta.setMiPiace(0);
		repo.save(ricetta);
		
		List<ImmagineForm> imgFormList = new ArrayList<ImmagineForm>();
		Immagine newImmagine = new Immagine();
		List<Immagine> ricettaImmagine = new ArrayList<Immagine>();
		for(ImmagineForm img : immagineList.getListaImmaginiForm()) {
			if(img.getContent()!=null) {
				imgFormList.add(img);				
			}
		}
		for(ImmagineForm imgForm : imgFormList) {
			byte[] contentSerialized = imgForm.getContent().getBytes();
			newImmagine.setContent(contentSerialized);
			newImmagine.setRicetta(ricetta);
			ricettaImmagine.add(newImmagine);
			imgRepo.save(newImmagine);	
		}
		
		
		List<Ingrediente> ingList = new ArrayList<Ingrediente>();
		for(Ingrediente ing : ingredienteList.getIngredienti()) {
			if(ing!=null) {
				if(ing.getNome()!=null || !ing.getNome().isEmpty() || !Strings.isBlank8(ing.getNome())) {
					if(ing.getQuantita()!=null || !ing.getQuantita().isEmpty() || !Strings.isBlank8(ing.getQuantita())) {
						ing.setRicetta(ricetta);
						ingList.add(ing);
						ingredienteRepo.save(ing);
					}
				}
			}
		}
		
		
		ricetta.setImmagini(ricettaImmagine);
		ricetta.setIngrediente(ingList);
		ricetta.setIsVegan(isVegan(ricetta));
		ricetta.setIsVegetarian(isVegetarian(ricetta));
		

		return repo.save(ricetta);
	}

	// Read
	public List<Ricetta> findAllSortedByRecent() {
		return repo.findAll(Sort.by("dataDiCreazione"));
	}

	public Ricetta getById(Integer id) {
		return repo.getById(id);
	}

	public boolean isVegan(Ricetta ricetta) {
		if(ricetta != null) {
			for(Ingrediente i : ricetta.getIngrediente()) {
				if(i.getIsVegan()) {
					return true;
				}else if(i.getIsVegan() != null || !i.getIsVegan()) {
					return false;
				}
			}
		} 
		return false;
		
	}
	
	public boolean isVegetarian(Ricetta ricetta) {		
		if(ricetta != null) {			
			for(Ingrediente i : ricetta.getIngrediente()) {
				if(!i.getIsVegetarian()) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public List<Ricetta> findByTitolo(String keyword) {
		return repo.findByTitoloContainingIgnoreCaseOrderByDataDiCreazione(keyword);
	}

	public List<Ricetta> findLastSevenDays() {
		return repo.findLastSevenDays();
	}
	
	public List<Ricetta> findMostViewed(){
		List<Ricetta> lista = repo.findAll(Sort.by(Direction.DESC, "visualizzazioni"));
		List<Ricetta> piuVisualizzate = new ArrayList<Ricetta>();
		int i = 0;
		while (i < 10 && i < lista.size()) {
			piuVisualizzate.add(lista.get(i));
			i++;
		}
		return piuVisualizzate;
	}

	Comparator<Ricetta> compareByComments = new Comparator<Ricetta>() {
		@Override
		public int compare(Ricetta o1, Ricetta o2) {
			Integer o1Size = o1.getCommenti().size();
			Integer o2Size = o2.getCommenti().size();
			return o1Size.compareTo(o2Size);
		}
	};

	public List<Ricetta> findMostCommented() {
		List<Ricetta> list = repo.findAll();
		List<Ricetta> piuCommentate = new ArrayList<Ricetta>();
		list.sort(compareByComments);
		int i = 0;
		while (i < 6 && i < list.size()) {
			piuCommentate.add(list.get(i));
			i++;
		}
		return piuCommentate;
	}

	public List<Ricetta> findSixMostRecent() {
		List<Ricetta> lista = repo.findAll(Sort.by(Direction.DESC, "dataDiCreazione"));
		List<Ricetta> piuRecenti = new ArrayList<Ricetta>();
		int i = 0;
		if (!lista.isEmpty()) {
			while (i < 6 && i < lista.size()) {
				piuRecenti.add(lista.get(i));
				i++;
			}
		}
		return piuRecenti;
	}

//	public Immagine getARandomImg(Ricetta ricetta) {
//		List<Immagine> list = ricetta.getImmagini();
//		Random rng = new Random();
//		int upperbound = list.size();
//		int intRng = rng.nextInt(upperbound);
//		return list.get(intRng);
//	}

	// Update
	public Ricetta update(Ricetta ricetta) {
		LocalDateTime dataDiCreazione = repo.getById(ricetta.getId()).getDataDiCreazione();
		Integer visualizzazioni = repo.getById(ricetta.getId()).getVisualizzazioni();
		ricetta.setDataDiCreazione(dataDiCreazione);
		ricetta.setVisualizzazioni(visualizzazioni);
		ricetta.setIsVegan(isVegan(ricetta));
		ricetta.setIsVegetarian(isVegetarian(ricetta));
		ricetta.setMiPiace(repo.getById(ricetta.getId()).getMiPiace());
		return repo.save(ricetta);
	}

	public Ricetta visualizzazioniPiuUno(Ricetta ricetta) {
		ricetta.setVisualizzazioni(ricetta.getVisualizzazioni() + 1);
		return repo.save(ricetta);
	}
	
	public Ricetta miPiace(Ricetta ricetta) {
		ricetta.setMiPiace(ricetta.getMiPiace() + 1);
		return repo.save(ricetta);
	}
	
	//Delete

	public void deleteById(Integer id) {
		repo.deleteById(id);
	}

	
	
}
