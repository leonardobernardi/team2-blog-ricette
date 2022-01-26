package org.generation.italy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.generation.italy.model.IngredientiRicetta;
import org.generation.italy.model.Ricetta;
import org.generation.italy.repository.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class RicettaService {

	@Autowired
	private RicettaRepository repo;

	// Create
	public Ricetta create(Ricetta ricetta) {
		ricetta.setDataDiCreazione(LocalDateTime.now());
		ricetta.setVisualizzazioni(0);
		// ricetta.setIsVegan(isVegan(ricetta));
		// ricetta.setIsVegetarian(isVegetarian(ricetta));
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
		if (ricetta != null) {
			for (IngredientiRicetta i : ricetta.getIngredienti()) {
				if (!i.getIngrediente().getIsVegan()) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean isVegetarian(Ricetta ricetta) {
		if (ricetta != null) {
			for (IngredientiRicetta i : ricetta.getIngredienti()) {
				if (!i.getIngrediente().getIsVegetarian()) {
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

	@SuppressWarnings("null")
	public List<Ricetta> findMostViewed() {
		List<Ricetta> lista = repo.findAll(Sort.by(Direction.DESC, "visualizzazioni"));
		List<Ricetta> piuVisualizzate = null;
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
		return repo.save(ricetta);
	}

	public Ricetta visualizzazioniPiuUno(Ricetta ricetta) {
		ricetta.setVisualizzazioni(ricetta.getVisualizzazioni() + 1);
		return repo.save(ricetta);
	}

	// Delete
	public void deleteById(Integer id) {
		repo.deleteById(id);
	}

}
