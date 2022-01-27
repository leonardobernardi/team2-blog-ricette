package org.generation.italy.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.generation.italy.model.Immagine;
import org.generation.italy.model.ImmagineForm;
import org.generation.italy.service.ImmagineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping ("/imm") //temp.
public class ImmagineController {
	
	@Autowired
	private ImmagineService service;
	
	//temp. using index3
	@GetMapping("/admin/imm/crea")
	public String index (Model model) {
		model.addAttribute("immagine", new ImmagineForm());
		model.addAttribute("list", service.findAll());
		return "index3";
		
	}
	
	@PostMapping("/admin/imm/crea")
	public String create(@Valid @ModelAttribute("immagine") ImmagineForm immagine, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			Model model) {
		if(immagine.getContent() == null || immagine.getContent().isEmpty()) {
			bindingResult.addError(new ObjectError("content", "Prego aggiungere un'immagine"));
		}
		if(bindingResult.hasErrors()) {
			model.addAttribute("list", service.findAll());
			return "index3";
		}
		try {
			service.create(immagine);
			redirectAttributes.addFlashAttribute("successMessage", "L'immagine è stata aggiunta");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Non è stato possibile aggiungere un'immagine");
			e.printStackTrace();
		}
		return "redirect:/imm";
		
	}
	
	//delete
	@GetMapping("/admin/imm/cancella/{id}")
	public String delete (@PathVariable("id") Integer id) {
		if(service.getById(id) != null) {
			service.deleteById(id);
		}
		return "redirect:/imm";
	
		
	}
	
	
	
	@RequestMapping(value = "/{id}/immagine", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImmagineContent(@PathVariable Integer id){
		Immagine immagine = service.getById(id);
		byte[] immagineContent = immagine.getContent();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(immagineContent, headers, HttpStatus.OK);
	}
	
	
	
	
	

}
