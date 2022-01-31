package org.generation.italy.controller;

import org.generation.italy.service.CommentoService;
import org.generation.italy.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/commenti")
public class CommentoController {

	@Autowired
	private CommentoService service;
	
	  @Autowired
	  private RicettaService ricettaService;
	
	@GetMapping
	public String gestioneCommenti(Model model){
		model.addAttribute("lista", ricettaService.findAllSortedByRecent());
		return "/admin/lista-commenti";
	}
	
	@GetMapping("/elimina/{id}")
	public String deleteCommentoById(@PathVariable("id") Integer id) {
		service.deleteCommentoById(id);
	return "redirect:/admin/commenti";
	}
}
