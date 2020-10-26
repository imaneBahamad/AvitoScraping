package com.home.scrap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.home.entities.Annonce;
import com.home.entities.Categorie;
import com.home.entities.SousCategorie;

public class AnnonceScrap {
	
	private static Categorie[] cats = {new Categorie("Informatique et multimédia"),new Categorie("Véhicules")};
	
	private static SousCategorie[] s_cats = {
			new SousCategorie("téléphones", cats[0]),
			new SousCategorie("tablettes", cats[0]),
			new SousCategorie("ordinateurs_portables", cats[0]),
			new SousCategorie("ordinateurs_bureau", cats[0]),
			new SousCategorie("accessoires_informatique_et_gadgets", cats[0]),
			new SousCategorie("jeux_vidéo_et_consoles", cats[0]),
			new SousCategorie("appareils_photo_cameras", cats[0]),
			new SousCategorie("télévisions", cats[0]),
			new SousCategorie("image_et_son", cats[0]),
			/*new SousCategorie("voitures", cats[1]),
			new SousCategorie("motos", cats[1]),
			new SousCategorie("vélos", cats[1]),
			new SousCategorie("véhicules_professionnels", cats[1]),
			new SousCategorie("bateaux", cats[1]),
			new SousCategorie("pièces_et_accessoires_véhicules", cats[1]),*/
	};
	
	public static ArrayList<Annonce> scrapSCategorie(SousCategorie s_cat) {        
        Document doc = null;
        ArrayList<Annonce> annonces= new ArrayList<Annonce>();
        
        int i=1;
        boolean good = true;
        do {
	        String url = "https://www.avito.ma/fr/maroc/"+s_cat.getTitle()+"-à_vendre?o="+i;
			try {
				doc = Jsoup.connect(url).timeout(6000).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Elements el = doc.select("div.listing");
	
			for (Element element : el.select("div.item")) {
				String age = element.select("div.item-age abbr small").text();
				if(age.contains("Aujourd'hui")) {
					//System.out.println("----"+age);
					String img_url = element.select("a div.image-and-nb div.item-img img.lazy").attr("data-original");
					//System.out.println(img_url);
					String info=element.select("div.item-info div.ctext2 div.ctext3 h2 a").text();
					String extra_info=element.select("div.item-info div.ctext2 div.ctext3 span.item-info-extra small a").text();
					//System.out.println(info+" "+info_extra);
					String prix=element.select("div.item-price span.mrs.fs14 span.price_value").text();
					//System.out.println(price+"DH");
					Annonce annonce = new Annonce(age, img_url, info, extra_info, prix, s_cat);
					annonces.add(annonce);
				}else {
					good=false;
				}	
			}
			i++;

        }while(good);
			
		return annonces;
    }	
	
	public static SousCategorie getSCategory(ArrayList<Annonce> annonces) {
		return annonces.get(0).getS_cat();
	}
	
	public static HashMap<SousCategorie, Integer> getAllStatistics() {
		HashMap<SousCategorie, Integer> map = new HashMap<SousCategorie, Integer>();
		
		for (int i = 0; i < s_cats.length; i++) {
			map.put(s_cats[i], scrapSCategorie(s_cats[i]).size());
		}
		return map;
	}

}
