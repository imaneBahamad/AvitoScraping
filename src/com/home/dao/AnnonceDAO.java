package com.home.dao;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.home.entities.Annonce;
import com.home.entities.Categorie;
import com.home.entities.SousCategorie;

import com.home.db.dbconnect;
import com.home.entities.values;

public class AnnonceDAO {
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
	
	public static HashMap<String, Integer> scrapForWeek() {
			//pour stocker les jours de type STRING
			HashMap<String, Integer> test = new HashMap<String, Integer>();
			
			HashMap<SousCategorie, Annonce> total = new HashMap<SousCategorie, Annonce>();


			dbconnect db =new dbconnect();

			
			HashMap<Integer, String> Month = new HashMap<Integer, String>();
			Month.put(1, "Jan");
			Month.put(2, "Févr");
			Month.put(3, "Mars");
			Month.put(4, "Avr");	
			Month.put(5, "Mai");		
			Month.put(6, "Juin");		
			Month.put(7, "Juill");		
			Month.put(8, "Août");		
			Month.put(9, "Sep");		
			Month.put(10, "Oct");		
			Month.put(11, "Nov");		
			Month.put(12, "Déc");
			
			
			LocalDate today = LocalDate.now();
			
			// stoker le mois courant dans cette variable
			
			String MM="";		
			for (int mon : Month.keySet()) {
				if (mon==today.getMonthValue()) {
					MM = Month.get(mon);
				}
			}

			ArrayList <Character> list = new ArrayList<Character>();
			
			try {
				
				int i=0 ;
				Document doc = Jsoup.connect("https://www.avito.ma").get();

				Elements tmp = doc.select("div.search-span select#catgroup option");

				for (Element categories:tmp) {
					i++;
					String s="";
								
					//parcourir la classe values pour obtenir les categories 
					for (values V : values.values()) {
						
						String vall=Integer.toString(V.getval());
						if (categories.attr("value").intern().equals(vall)) {
								
							String cat = categories.text().toLowerCase();
						
							//traitement des categories ayant un espace
							for (int j = 0; j < cat.length(); j++) {
								list.add(cat.charAt(j));
							}
							
							//zid underscore
							for (int k = 0; k < list.size(); k++) {
								
								if (list.get(k).equals(' ')) {
									list.set(k,'_');
								}
								s+=list.get(k);
							}
							
							//vider la liste pour traiter la 2eme valeur (values|options)
							list.clear();
							
							if (!s.equals("")) {
								System.out.println("valeur"+i+"(\""+s+"\"),");
							
								//stocker la categories dans la liste Categorie
								SousCategorie s_cat =new SousCategorie(s,cats[1]);
								
								int l =0;
						
								//parcourir toute les pages du produit
			
								for (int j = 1; j < 222; j++) {
							
										String url="https://www.avito.ma/fr/maroc/"+s+"-à_vendre?o="+j;
										
										Document docs = Jsoup.connect(url).get();
										Elements tmpextra=docs.select("div.listing-thumbs div.li-hover");
					
										for (Element info : tmpextra) {
	
											String dateItem=info.select("div.item-age strong").text();
								
											//comparer dateItem avec les septs jours precedents
											if (dateItem.equals("Aujourd'hui") || dateItem.equals("Hier") || dateItem.equals(today.getDayOfMonth()-2+" "+MM)
													|| dateItem.equals(today.getDayOfMonth()-3+" "+MM)|| dateItem.equals(today.getDayOfMonth()-4+" "+MM)
													|| dateItem.equals(today.getDayOfMonth()-5+" "+MM)|| dateItem.equals(today.getDayOfMonth()-6+" "+MM)) {
												l++;
											
												String nameItem=info.select("div.ctext1 div.ctext2 div.fs12 h2.fs14 a").text();
												String priceItem=info.select("div.item-price span.fs14 span.price_value").text();
												
												Annonce annonce =new Annonce(dateItem,"","","","",s_cat);
												total.put(s_cat, annonce);
												System.out.println(l+" Name : "+nameItem+" date : "+dateItem+" Price : "+priceItem+" .");
											}
										}
									}
								}
							}
						}
					}
				

				
				for (int j = 0; j <total.size(); j++) {
					int sommeA=0,sommeA1=0,sommeA2=0,sommeA3=0,sommeA4=0,sommeA5=0,sommeA6=0;
					
					
					for (int i1 = 0; i1 <total.size(); i1++) {
						
						if (total.get(i1).getAge().contains("Aujourd'hui")) {	
							
							test.put(today.getDayOfMonth()+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA);
							
						}else if (total.get(i1).getAge().contains("Hier")) {				
							
							test.put(today.getDayOfMonth()-1+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA1);
							
						}
						for (int mon : Month.keySet()) {
							
							if (mon==today.getMonthValue() && total.get(i1).getAge().contains(today.getDayOfMonth()-2+" "+Month.get(mon))) {
								
								test.put(today.getDayOfMonth()-2+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA2);
								
							}else if (mon==today.getMonthValue() && total.get(i1).getAge().contains(today.getDayOfMonth()-3+" "+Month.get(mon))) {
							
								test.put(today.getDayOfMonth()-3+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA3);
								
							}else if (mon==today.getMonthValue() && total.get(i1).getAge().contains(today.getDayOfMonth()-4+" "+Month.get(mon))) {
							
								test.put(today.getDayOfMonth()-4+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA4);
								
							}else if (mon==today.getMonthValue() && total.get(i1).getAge().contains(today.getDayOfMonth()-5+" "+Month.get(mon))) {
							
								test.put(today.getDayOfMonth()-5+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA5);
							}else if (mon==today.getMonthValue() && total.get(i1).getAge().contains(today.getDayOfMonth()-6+" "+Month.get(mon))) {	
							
							test.put(today.getDayOfMonth()-6+"-"+today.getMonthValue()+"-"+today.getYear(), ++sommeA6);
							
							}
						}
					}
					
					 for (String t : test.keySet()) {
						db.saisieCategories2(total.get(j).getS_cat().getTitle(), test.get(t),t);
					}
					 
					System.out.println(total.get(j).getS_cat().getTitle());
					

					System.out.println(total.size());
					

					
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR : "+e);
			}
			
			return test;
		
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
