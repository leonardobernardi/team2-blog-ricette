package org.generation.italy.model;

import org.springframework.web.multipart.MultipartFile;

public class ImmagineForm {
	
	private Integer id;
	private Ricetta ricetta;
	private MultipartFile content;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Ricetta getRicetta() {
		return ricetta;
	}
	public void setRicetta(Ricetta ricetta) {
		this.ricetta = ricetta;
	}
	public MultipartFile getContent() {
		return content;
	}
	public void setContent(MultipartFile content) {
		this.content = content;
	}
	
	
	

}
