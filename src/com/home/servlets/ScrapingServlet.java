package com.home.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.home.scrap.AnnonceScrap;
import com.home.entities.SousCategorie;

/**
 * Servlet implementation class ScrapingServlet
 */
@WebServlet("/ScrapingServlet")
public class ScrapingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScrapingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<SousCategorie,Integer> statistics = AnnonceScrap.getAllStatistics(); 

		Iterator<Integer> it = statistics.values().iterator();
		ArrayList<Integer> values= new ArrayList<Integer>();
		
		while(it.hasNext()) {
			values.add(it.next());
		}
		
		ArrayList<SousCategorie> keys = new ArrayList<SousCategorie>(statistics.keySet());
		ArrayList<String> categories = new ArrayList<String>();
		for (SousCategorie key : keys) {
			categories.add(key.getTitle());
		}
		
		request.setAttribute("statistics", statistics);
		request.setAttribute("values", values);
		request.setAttribute("categories", categories);

		this.getServletContext().getRequestDispatcher("/WEB-INF/afficheStatistics.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
