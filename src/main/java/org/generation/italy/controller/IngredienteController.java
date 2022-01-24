package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Ingrediente;
import org.generation.italy.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping ("/admin/ingredienti")
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	// create
	
	@GetMapping("/create")
	//temp. "/admin/ingredienti/edit" etc
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("ingrediente", new Ingrediente());
		return "/admin/ingredienti/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			return "/admin/ingredienti/edit";
		}
		ingredienteService.create(formIngrediente);
		return "redirect:/admin/ingredienti";
	}
	
	// edit
	
	@GetMapping("/admin/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("ingrediente", ingredienteService.getById(id));
		return "/admin/ingredienti/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doEdit (@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, 
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "/admin/ingredienti/edit";
		}
		ingredienteService.create(formIngrediente);
		return "redirect:/admin/ingredienti";
	}
	
	// delete
	
	@GetMapping("/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		if(ingredienteService.getById(id) == null) {
		}
		ingredienteService.deleteById(id);
		return "redirect:/admin/ingredienti";
	}

}
