package org.generation.italy.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.generation.italy.model.Commento;
import org.generation.italy.model.Immagine;
import org.generation.italy.model.ImmagineForm;
import org.generation.italy.model.ImmagineList;
import org.generation.italy.model.Ingrediente;
import org.generation.italy.model.IngredienteList;
import org.generation.italy.model.Ricetta;
import org.generation.italy.repository.CommentoRepository;
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
	
	@Autowired
	private CommentoRepository comRepo;
	
	// Create
	public Ricetta create(Ricetta ricetta, IngredienteList ingredienteList, ImmagineList immagineList) throws IOException {
		ricetta.setDataDiCreazione(LocalDateTime.now());
		ricetta.setVisualizzazioni(0);
		ricetta.setMiPiace(0);
		repo.save(ricetta);
		List<ImmagineForm> imgFormList = new ArrayList<ImmagineForm>();		
		List<Immagine> ricettaImmagine = new ArrayList<Immagine>();
		for(ImmagineForm img : immagineList.getListaImmaginiForm()) {
			if(img.getContent()!=null & img.getContent().getSize()!=0) {
				imgFormList.add(img);				
			}
		}
		for(ImmagineForm imgForm : imgFormList) {
			byte[] contentSerialized = imgForm.getContent().getBytes();
			Immagine newImmagine = new Immagine();
			newImmagine.setContent(contentSerialized);
			newImmagine.setRicetta(ricetta);
			ricettaImmagine.add(newImmagine);
			imgRepo.save(newImmagine);	
		}
		List<Ingrediente> ingList = new ArrayList<Ingrediente>();
		for(Ingrediente ing : ingredienteList.getIngredienti()) {
			if(ing!=null) {
				if(ing.getNome()!=null && !ing.getNome().isEmpty()) {
					if(ing.getQuantita()!=null && !ing.getQuantita().isEmpty()) {
						if(ing.getIsVegan()==true) {
							ing.setIsVegetarian(true);
						}
						if(ing.getIsVegan()==null) {
							ing.setIsVegan(false);
						}
						if(ing.getIsVegetarian()==null) {
							ing.setIsVegetarian(false);
						}
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

	// for later
	public boolean isVegan(Ricetta ricetta) {
		if(ricetta != null) {
			if (ricetta.getIngrediente().size()>0) {
				for (Ingrediente i : ricetta.getIngrediente()) {
					if (!i.getIsVegan()) {
						return false;
					}
				}
				return true;
			}
		} 
		return false;

	}

	public boolean isVegetarian(Ricetta ricetta) {		
		if(ricetta != null) {	
			if(ricetta.getIngrediente().size()>0) {
				for(Ingrediente i : ricetta.getIngrediente()) {
					if(!i.getIsVegetarian()) {
						return false;
					}
				}
				return true;
			}

		} 
			return false;

	}
	
	public List<Ricetta> findByCategoria(Integer categoryId){
		return repo.findByCategoriaContaining(categoryId);
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
	
	public Ricetta updateImmagini(Integer id, ImmagineList immagineList ) throws IOException {
		Ricetta ricetta = repo.getById(id);
		svuotaImmagini(id);
		List<ImmagineForm> imgFormList = new ArrayList<ImmagineForm>();		
		List<Immagine> ricettaImmagine = new ArrayList<Immagine>();
		for(ImmagineForm img : immagineList.getListaImmaginiForm()) {
			if(img.getContent()!=null & img.getContent().getSize()!=0) {
				imgFormList.add(img);				
			}
		}
		for(ImmagineForm imgForm : imgFormList) {
			byte[] contentSerialized = imgForm.getContent().getBytes();
			Immagine newImmagine = new Immagine();
			newImmagine.setContent(contentSerialized);
			newImmagine.setRicetta(ricetta);
			ricettaImmagine.add(newImmagine);
			imgRepo.save(newImmagine);	
		}
		ricetta.setImmagini(ricettaImmagine);
		return repo.save(ricetta);			
	}
	

	public void cancellaIngrPrecedenti(Integer id) {
		List<Ingrediente> listaIng = repo.getById(id).getIngrediente();
		for(Ingrediente ing : listaIng) {
			ingredienteRepo.delete(ing);
		}
		listaIng.clear();
		repo.getById(id).setIngrediente(listaIng);
		
	}
	
	public Ricetta updateIngredienti(Integer id, IngredienteList ingredienteList) {
		Ricetta ricetta = repo.getById(id);
		cancellaIngrPrecedenti(id);
		List<Ingrediente> ingList = new ArrayList<Ingrediente>();
		for (Ingrediente ing: ingredienteList.getIngredienti()) {
			if (ing != null) {
				if (ing.getNome() != null && !ing.getNome().isEmpty()) {
					if (ing.getQuantita() != null && !ing.getQuantita().isEmpty()) {
						if (ing.getIsVegan() == true) {
							ing.setIsVegetarian(true);
						}
						if (ing.getIsVegan() == null) {
							ing.setIsVegan(false);
						}
						if (ing.getIsVegetarian() == null) {
							ing.setIsVegetarian(false);
						}
						ing.setRicetta(ricetta);
						ingList.add(ing);
						ingredienteRepo.save(ing);
					}
				}
			}
		}
		ricetta.setIngrediente(ingList);
		ricetta.setIsVegan(isVegan(ricetta));
		ricetta.setIsVegetarian(isVegetarian(ricetta));
		return repo.save(ricetta);
	}

	
	//Update vecchia ricetta
	public Ricetta updateRicetta(Ricetta ricetta, Integer id) {
		Ricetta ricettaDaModificare = repo.getById(id);
		ricettaDaModificare.setTitolo(ricetta.getTitolo());
		ricettaDaModificare.setTempoDiPreparazione(ricetta.getTempoDiPreparazione());
		ricettaDaModificare.setLivelloDiDifficolta(ricetta.getLivelloDiDifficolta());
		ricettaDaModificare.setDescrizione(ricetta.getDescrizione());
		ricettaDaModificare.setTestoDellaRicetta(ricetta.getTestoDellaRicetta());
		ricettaDaModificare.setDataDiCreazione(repo.getById(id).getDataDiCreazione());
		ricettaDaModificare.setVisualizzazioni(repo.getById(id).getVisualizzazioni());
		ricettaDaModificare.setMiPiace(repo.getById(id).getMiPiace());
		return repo.save(ricettaDaModificare);
	}
	
	
	//Delete

	public void deleteById(Integer id) {
		Ricetta ricetta = repo.getById(id);
		List<Immagine> imgList = ricetta.getImmagini();
		for(Immagine img : imgList) {
			imgRepo.delete(img);
		}
		List<Commento> comList = ricetta.getCommenti();
		for(Commento com : comList) {
			comRepo.delete(com);
		}
		repo.deleteById(id);
	}
	
	public void svuotaImmagini(Integer id) {
		List<Immagine> list = repo.getById(id).getImmagini();
		for(Immagine img : list) {
			imgRepo.delete(img);
		}
		list.clear();
		repo.getById(id).setImmagini(list);
	}

	
	
}
