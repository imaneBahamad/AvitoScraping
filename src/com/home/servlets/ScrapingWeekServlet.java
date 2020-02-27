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

import com.home.dao.AnnonceDAO;
import com.home.entities.SousCategorie;

/**
 * Servlet implementation class ScrapingWeekServlet
 */
@WebServlet("/ScrapingWeekServlet")
public class ScrapingWeekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScrapingWeekServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String,Integer> statisticsWeek = AnnonceDAO.scrapForWeek(); 
		Iterator<Integer> it = statisticsWeek.values().iterator();
		
		ArrayList<Integer> values= new ArrayList<Integer>();
		
		while(it.hasNext()) {
			values.add(it.next());
		}
		
		ArrayList<String> keys = new ArrayList<String>(statisticsWeek.keySet());
		
		request.setAttribute("statisticsWeek", statisticsWeek);
		request.setAttribute("keys", values);

		
		this.getServletContext().getRequestDispatcher("/WEB-INF/afficheWeekStatistics.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
