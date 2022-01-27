package org.generation.italy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.generation.italy.model.Commento;
import org.generation.italy.model.Immagine;
import org.generation.italy.model.ImmagineForm;
import org.generation.italy.model.ImmagineList;
import org.generation.italy.model.Ingrediente;
import org.generation.italy.model.IngredienteList;
import org.generation.italy.model.Ricetta;
import org.generation.italy.service.CommentoService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class RicettaController {

	@Autowired
	private RicettaService service;
	
	@Autowired
	private CommentoService commentoService;
	
	
	//Homepage
	@GetMapping
	public String mostRecent(Model model) {
		List<Ricetta> list = service.findSixMostRecent();
		if (list!=null) {
			for (Ricetta ricetta : list) {
				if (!ricetta.getImmagini().isEmpty()) {
					Immagine img = ricetta.getImmagini().get(0);
					model.addAttribute("img" + list.indexOf(ricetta), img);
				}
			}

		if (service.findSixMostRecent() != null) {
			model.addAttribute("lista", service.findSixMostRecent());
		}
		}
		return "/home/index";
	}
	
	//Create
		@GetMapping("/admin/ricetta/crea")
		public String create(Model model) {
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
			return "/ricetta/edit";
		}

		@PostMapping("/admin/ricetta/crea")
		public String create(@Valid @ModelAttribute("ricetta") Ricetta formRicetta, 
				BindingResult bindingResult,
				@ModelAttribute ("ingredientiForm") IngredienteList ingredienteList,
				@ModelAttribute ("immaginiForm") ImmagineList immagineList,
				Model model) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("edit", false);
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
			model.addAttribute("piuRecenti", service.findLastSevenDays());
			model.addAttribute("piuViste", service.findMostViewed());
			model.addAttribute("piuCommentate", service.findMostCommented());
			return "/admin/attivita";
		}
		
		@GetMapping("/ricetta/{id}")
		public String detail(@PathVariable("id") Integer id, Model model) {
			service.visualizzazioniPiuUno(service.getById(id));
			model.addAttribute("ricetta", service.getById(id));		
			model.addAttribute("commento", new Commento());
			model.addAttribute("list", service.getById(id).getCommenti());
			if (!service.getById(id).getImmagini().isEmpty()) {
				for (Immagine img : service.getById(id).getImmagini()) {
					model.addAttribute("img" + service.getById(id).getImmagini().indexOf(img), img);
				} 
			}
			return "ricetta/dettagli";
		}
		
		
		@PostMapping("/ricetta/commento/{id}")
		public String addComment(@PathVariable("id") Integer id,
				@Valid @ModelAttribute("commento") Commento formCommento, BindingResult bindingResult,
				Model model, RedirectAttributes redirectAttributes) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("edit", false);
			} 
			commentoService.create(formCommento, id);
			redirectAttributes.addAttribute("commento", new Commento());
			return "redirect:/ricetta/"+id;
		}
		
		
		@RequestMapping(value = "/{id}/img", produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE )
		public ResponseEntity<byte[]> getImgContent(@PathVariable Integer id){
			byte[] imgContent = service.getById(id).getImmagini().get(0).getContent();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
			return new ResponseEntity<byte[]>(imgContent, headers, HttpStatus.OK);
		}
		
		
		
		//Update
		@GetMapping("/admin/ricetta/modifica/{id}")
		public String edit(@PathVariable("id") Integer id, Model model) {
			model.addAttribute("edit", true);
			model.addAttribute("ricetta", service.getById(id));
			return "ricetta/dettagli";
		}

		@PostMapping("/admin/ricetta/modifica/{id}")
		public String doUpdate(@Valid @ModelAttribute("ricetta") Ricetta formRicetta, BindingResult bindingResult, Model model){
			if(bindingResult.hasErrors()) {
				model.addAttribute("edit", true);
				return "ricetta/dettagli";
			}
			service.update(formRicetta);
			return "redirect:/home/index";
		}
		
		//Delete 
		@GetMapping("/admin/ricetta/cancella/{id}")
		public String delete(@PathVariable("id") Integer id) {
			if(service.getById(id) == null) {
				//messaggio d'errore
			}
			service.deleteById(id);
			return "redirect:/home/index";
		}
}
