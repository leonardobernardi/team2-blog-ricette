package org.generation.italy.controller;

import org.generation.italy.service.CommentoService;
import org.generation.italy.service.EmailService;
import org.generation.italy.service.RicettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/commenti")
public class CommentoController {

	@Autowired
	private CommentoService service;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RicettaService ricettaService;

	
	@GetMapping
	public String gestioneCommenti(Model model){
		model.addAttribute("admin", true);
		model.addAttribute("lista", ricettaService.findAllSortedByRecent());
//		model.addAttribute("categorie", catService.findAll());
		return "/admin/lista-commenti";
	}
	
	@GetMapping("/elimina/{id}")
	public String deleteCommentoById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Model model) {
		model.addAttribute("admin", true);
//		redirectAttributes.addAttribute("categorie", catService.findAll());
		service.deleteCommentoById(id);
	return "redirect:/admin/commenti";
	}
	
	@GetMapping("email")
	public String editEmail(Model model) {
//		model.addAttribute("categorie", catService.findAll());
		model.addAttribute("admin", true);
		model.addAttribute("lista", emailService.findIsBan());
		return "/admin/mail-ban";
	}
	
	
	@GetMapping("/ban/{id}")
	public String revertBan(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
		model.addAttribute("admin", true);
//		redirectAttributes.addAttribute("categorie", catService.findAll());
		emailService.revertBan(id);
		return "redirect:/admin/commenti";
	}
}
