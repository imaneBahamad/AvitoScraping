package com.home.entities;

public class Annonce {
	
	
	private String age;
	private String img_url;
	private String info;
	private String extra_info;
	private String prix;
	private SousCategorie s_cat;
	
	public Annonce(String age, String img_url, String info, String extra_info, String prix, SousCategorie s_cat) {
		super();
		this.age = age;
		this.img_url = img_url;
		this.info = info;
		this.extra_info = extra_info;
		this.prix = prix;
		this.s_cat = s_cat;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getExtra_info() {
		return extra_info;
	}

	public void setExtra_info(String extra_info) {
		this.extra_info = extra_info;
	}

	public SousCategorie getS_cat() {
		return s_cat;
	}

	public void setS_cat(SousCategorie s_cat) {
		this.s_cat = s_cat;
	}

}
