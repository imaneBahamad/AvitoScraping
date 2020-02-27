package com.home.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class dbconnect {

	private Connection con ;
	private Statement st;
	private ResultSet rst ;

	
	
	public dbconnect()  {
		
		LocalDate today = LocalDate.now();
	   
	        try {
	            // The newInstance() call is a work around for some
	            // broken Java implementations

	            Class.forName("com.mysql.jdbc.Driver");//chemin du driver 
	            con =DriverManager.getConnection("jdbc:mysql://localhost:3036/scrap","root","");
	            System.out.println("Connexion établie");
	            st=con.createStatement();          
	            st.executeUpdate("DELETE FROM cat");
            	
	            for (int j = 0; j <= 6; j++) {
	            	System.out.println((today.getDayOfMonth()-j)+"-"+today.getMonthValue()+"-"+today.getYear());
	            		
	            	String query ="INSERT INTO cat (date) VALUES (\""+(today.getDayOfMonth()-j)+"-"+today.getMonthValue()+"-"+today.getYear()+"\")";
	            	st.executeUpdate(query);
				}
	            
	            System.out.println("the connet is correcte !!! ");
	            
	            		
	        } catch (Exception ex) {
	            // handle the error
	        	System.out.println("ERROR : "+ex );
	        }
	    }


		public void saisieCategories2(String name,int nbr,String date) {	
			try {
		        String query = "UPDATE cat SET "+name+"=\""+nbr+"\" WHERE date =\""+date+"\"";//erroe of using simple cote hit possible tkon fstriing lighadi dkhle l labase dyalk wnta ma3arafch alor khdem b antislash double cote \"
				st.executeUpdate(query);
		        System.out.println("Insert information corectly !");
		        
		        		
		    } catch (Exception ex) {
		        // handle the error
		    	System.out.println("ERROR : "+ex );
		    }
		}
	
}
