package org.generation.italy.model;

import java.util.List;

public class ImmagineList {
	
	List<ImmagineForm> listaImmaginiForm;


	
	public ImmagineList(List<ImmagineForm> listaImmaginiForm) {
		super();
		this.listaImmaginiForm = listaImmaginiForm;
	}



	public List<ImmagineForm> getListaImmaginiForm() {
		return listaImmaginiForm;
	}



	public void setListaImmaginiForm(List<ImmagineForm> listaImmaginiForm) {
		this.listaImmaginiForm = listaImmaginiForm;
	}



	public void addImmagine(ImmagineForm immagine) {
		this.listaImmaginiForm.add(immagine);
	}

}
