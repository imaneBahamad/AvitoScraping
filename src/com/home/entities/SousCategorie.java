package com.home.entities;

public class SousCategorie {
	
	private String title;
	private Categorie categorie;

	public SousCategorie(String title,Categorie categorie) {
		super();
		this.title = title;
		this.categorie = categorie;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	

}
