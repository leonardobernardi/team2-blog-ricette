package org.generation.italy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Categoria;
import org.generation.italy.model.Commento;
import org.generation.italy.model.Immagine;
import org.generation.italy.model.ImmagineForm;
import org.generation.italy.model.ImmagineList;
import org.generation.italy.model.Ingrediente;
import org.generation.italy.model.IngredienteList;
import org.generation.italy.model.Ricetta;
import org.generation.italy.service.CategoriaService;
import org.generation.italy.service.CommentoService;
import org.generation.italy.service.ImmagineService;
import org.generation.italy.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class RicettaController {

	@Autowired
	private RicettaService service;
	
	@Autowired
	private CommentoService commentoService;
	
	@Autowired
	private ImmagineService imgService;
	
	@Autowired CategoriaService catService;
	
	//Homepage
	@GetMapping
	public String mostRecent(Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Ricetta> list;
		model.addAttribute("categorie", catService.findAll());
		if(keyword!=null && !keyword.isEmpty()) {
			list = service.findByTitolo(keyword);
		}else {
			list = service.findSixMostRecent();
		}
		
		if (list!=null) {
		if (service.findSixMostRecent() != null) {
			model.addAttribute("lista", list);
		}
		}
		return "/home/index";
	}
	
	//Create
		@GetMapping("/admin/ricetta/crea")
		public String create(Model model) {
			model.addAttribute("categorie", catService.findAll());
			List<Ingrediente> ingredientiList= new ArrayList<Ingrediente>(); 
			List<ImmagineForm> immaginiList = new ArrayList<ImmagineForm>();
			ImmagineList immaginiForm = new ImmagineList(immaginiList);
			for (int i = 0; i < 5; i++) {
				immaginiForm.addImmagine(new ImmagineForm());
			}
			IngredienteList ingredientiForm = new IngredienteList(ingredientiList);
			for (int i = 0; i < 30; i++) {
				ingredientiForm.addIngrediente(new Ingrediente());
			}
			model.addAttribute("immaginiForm", immaginiForm);
			model.addAttribute("ingredientiForm", ingredientiForm);
			model.addAttribute("edit", false);
			model.addAttribute("ricetta", new Ricetta());
			model.addAttribute("admin", true);
			return "/ricetta/edit";
		}

		@PostMapping("/admin/ricetta/crea")
		public String create(@Valid @ModelAttribute("ricetta") Ricetta formRicetta, 
				BindingResult bindingResult,
				@ModelAttribute ("ingredientiForm") IngredienteList ingredienteList,
				@ModelAttribute ("immaginiForm") ImmagineList immagineList,
				Model model, RedirectAttributes redirectAttributes) {
			redirectAttributes.addAttribute("categorie", catService.findAll());
			if(bindingResult.hasErrors()) {
				model.addAttribute("edit", false);
				model.addAttribute("admin", true);
				return "/ricetta/edit";
			}
			try {
				service.create(formRicetta, ingredienteList, immagineList);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			return "redirect:/";
		}
		
		//Read
		
		@GetMapping("/admin")
		public String admin(Model model) {
			model.addAttribute("categorie", catService.findAll());
			model.addAttribute("piuRecenti", service.findLastSevenDays());
			model.addAttribute("piuViste", service.findMostViewed());
			model.addAttribute("piuCommentate", service.findMostCommented());
			model.addAttribute("admin", true);
			return "/admin/attivita";
		}
		
		@GetMapping("/ricetta/{id}")
		public String detail(@PathVariable("id") Integer id, Model model) {
			model.addAttribute("categorie", catService.findAll());
			service.visualizzazioniPiuUno(service.getById(id));
			model.addAttribute("ricetta", service.getById(id));		
			model.addAttribute("commento", new Commento());
			model.addAttribute("list", service.getById(id).getCommenti());
//			if (!service.getById(id).getImmagini().isEmpty()) {
//				for (Immagine img : service.getById(id).getImmagini()) {
//					model.addAttribute("img" + service.getById(id).getImmagini().indexOf(img), img);
//				} 
//			}
			model.addAttribute("ingredienti", service.getById(id).getIngrediente());
			return "ricetta/dettagli";
		}
		
		
		@PostMapping("/ricetta/commento/{id}")
		public String addComment(@PathVariable("id") Integer id,
				@Valid @ModelAttribute("commento") Commento formCommento, BindingResult bindingResult,
				Model model, RedirectAttributes redirectAttributes) {
			redirectAttributes.addAttribute("categorie", catService.findAll());
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("edit", false);
			} 
			commentoService.create(formCommento, id);
			redirectAttributes.addAttribute("commento", new Commento());
			return "redirect:/ricetta/"+id;
		}
		
		@GetMapping("/categoria/{id}")
		public String getPerCategoria(@PathVariable("id") Integer id, Model model) {
			model.addAttribute("categorie", catService.findAll());
			List<Ricetta> list;
			List<Categoria> listaCategorie;
			listaCategorie = catService.findAll();
			model.addAttribute("categorie", listaCategorie);
			list = service.findByCategoria(id);
			model.addAttribute("lista", list);
			model.addAttribute("categoriaNome", catService.getById(id));
			return "/categorie/indexCategorie";
		}
		
		@RequestMapping(value = "/{id}/img", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE )
		public ResponseEntity<byte[]> getImgContent(@PathVariable Integer id){
			byte[] imgContent;
			if(service.getById(id).getImmagini().size()<=0) {
				imgContent = null;
			}else {
				imgContent = service.getById(id).getImmagini().get(0).getContent();
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
			return new ResponseEntity<byte[]>(imgContent, headers, HttpStatus.OK);
		}
		
		@RequestMapping(value = "detail/{id}/{imgId}/img", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE )
		public ResponseEntity<byte[]> getImgContentList(@PathVariable Integer id, @PathVariable Integer imgId){
		
			Immagine imgOfId = imgService.getById(id, imgId);
			byte[] imgContent = imgOfId.getContent();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
			return new ResponseEntity<byte[]>(imgContent, headers, HttpStatus.OK);
		}
		
		

		//Update
	
		
		@GetMapping("/admin/modifica")
		public String edit(Model model) {
			model.addAttribute("categorie", catService.findAll());
			model.addAttribute("lista", service.findAllSortedByRecent());
			model.addAttribute("admin", true);
			return "/admin/lista-ricette";
		}

//		@PostMapping("/admin/ricetta/modifica/{id}")
//		public String doUpdate(@Valid @ModelAttribute("ricetta") Ricetta formRicetta, BindingResult bindingResult, Model model){
//			if(bindingResult.hasErrors()) {
//				model.addAttribute("edit", true);
//				return "ricetta/dettagli";
//			}
//			service.update(formRicetta);
//			return "redirect:/home/index";
//		}

		
		@GetMapping("/admin/ricetta/modifica/{id}/immagini")
		public String editImg(@PathVariable("id") Integer id, Model model) {
			model.addAttribute("categorie", catService.findAll());
			List<ImmagineForm> immaginiList = new ArrayList<ImmagineForm>();
			ImmagineList immaginiForm = new ImmagineList(immaginiList);
			for (int i = 0; i < 5; i++) {
				immaginiForm.addImmagine(new ImmagineForm());
			}
			model.addAttribute("ricetta", service.getById(id));
			model.addAttribute("immaginiForm", immaginiForm);
			return "admin/edit-immagini";
		}
		
		@PostMapping("/admin/ricetta/modifica/{id}/immagini")
		public String doEditImg(@ModelAttribute ("immaginiForm") ImmagineList immagineList,
				@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
			redirectAttributes.addAttribute("categorie", catService.findAll());
			try {
				service.updateImmagini(id, immagineList);
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
			return "redirect:/ricetta/" + id;
		}


		
		//Update di ingredienti
		@GetMapping("/admin/ricetta/modifica/{id}/ingredienti")
		public String editIngredienti(@PathVariable("id") Integer id, Model model) {
			model.addAttribute("categorie", catService.findAll());
			List<Ingrediente> ingredientiList = new ArrayList<Ingrediente>();
			IngredienteList ingredientiForm = new IngredienteList(ingredientiList);
			for (int i = 0; i < 26; i++) {
				ingredientiForm.addIngrediente(new Ingrediente());
			}
			model.addAttribute("ingredientiForm", ingredientiForm);
			model.addAttribute("ricetta", service.getById(id));
			return "admin/edit-ingredienti";
		}
		
		@PostMapping("/admin/ricetta/modifica/{id}/ingredienti")
		public String doEditIngredienti (@ModelAttribute("ingredientiForm") 
		IngredienteList ingredientiList, @PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
			redirectAttributes.addAttribute("categorie", catService.findAll());
			service.updateIngredienti(id, ingredientiList);
			return "redirect:/ricetta/" + id;
			
		}

		
//		//doEdit -wip
//		@GetMapping("/admin/ricetta/modifica/{id}")
//		public String editRicetta (@PathVariable("id") Integer id, Model model) {
////			model.addAttribute("nuovaRicetta", new Ricetta());
//			model.addAttribute("vecchiaRicetta", service.getById(id));
//			return "/admin/edit-ricetta";
//		}
//		
//		@PostMapping("/admin/ricetta/modifica/{id}")
//		public String doEditRicetta (@Valid @ModelAttribute("vecchiaRicetta") 
//		Ricetta formRicetta, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//			if (bindingResult.hasErrors()) {
//				return "/admin/edit-ricetta";
//			}
//			service.update(formRicetta);
//			return "redirect:/home/index";
//		}
		

		
		//doEdit2 
		@GetMapping("/admin/ricetta/modifica/{id}")
		public String editRicetta (@PathVariable("id") Integer id, Model model) {
			model.addAttribute("categorie", catService.findAll());
			model.addAttribute("ricettaDaModificare", service.getById(id));
			return "/admin/edit-ricetta";
		}
		
		@PostMapping("/admin/ricetta/modifica/{id}")
		public String doEditRicetta (@Valid @ModelAttribute("ricettaDaModificare") 
		Ricetta formRicetta, @PathVariable("id") Integer id, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
			redirectAttributes.addAttribute("categorie", catService.findAll());
			if (bindingResult.hasErrors()) {
				return "/admin/edit-ricetta";
			}
			service.updateRicetta(formRicetta, id);
			return "redirect:/ricetta/" + id;
		}

		
		//Delete 
		@GetMapping("/admin/modifica/cancella/{id}")
		public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
			redirectAttributes.addAttribute("categorie", catService.findAll());
			if(service.getById(id) == null) {
				//messaggio d'errore
			}
			service.deleteById(id);
			return "redirect:/admin/modifica";
		}
}