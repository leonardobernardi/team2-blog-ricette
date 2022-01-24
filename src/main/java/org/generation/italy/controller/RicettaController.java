package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Ricetta;
import org.generation.italy.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RicettaController {

	@Autowired
	private RicettaService service;
	
	//Homepage
	@GetMapping
	public String mostRecent(Model model) {
		model.addAttribute("lista", service.findFiveMostRecent());
		return "index";
	}
	//Create
		@GetMapping("/admin/ricetta/crea")
		public String create(Model model) {
			model.addAttribute("edit", false);
			model.addAttribute("ricetta", new Ricetta());
			return "/ricetta/edit";
		}

		@PostMapping("/admin/ricetta/crea")
		public String create(@Valid @ModelAttribute("ricetta") Ricetta formRicetta, BindingResult bindingResult, Model model) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("edit", false);
			}
			service.create(formRicetta);
			return "redirect:/index";
		}
		
		//Read
		@GetMapping("/ricetta/{id}")
		public String detail(@PathVariable("id") Integer id, Model model) {
			model.addAttribute("ricetta", service.getById(id));
			return "ricetta/dettagli";
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
			return "redirect:/index";
		}
		
		//Delete 
		@GetMapping("/admin/ricetta/cancella/{id}")
		public String delete(@PathVariable("id") Integer id) {
			if(service.getById(id) == null) {
				//messaggio d'errore
			}
			service.deleteById(id);
			return "redirect:/index";
		}
}
