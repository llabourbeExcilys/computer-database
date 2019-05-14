package com.excilys.cdb.front.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.back.controller.Controller;
import com.excilys.cdb.back.dao.SortingField;
import com.excilys.cdb.back.dao.SortingOrder;
import com.excilys.cdb.back.dto.ComputerDTO;


@WebServlet(urlPatterns = {"/dashboard"})
public class DashBoard extends HttpServlet {

	private static final long serialVersionUID = 5700829257941123519L;

	private static Controller controller;
	
	private static int nbByPage = 10;
	private static int page = 1;
	private static String field = "";
	private static String order = "";
	
	private static SortingField sortingField;
	private static SortingOrder sortingOrder;
	
	@Override
    public void init() throws ServletException {
		super.init();
		ServletContext servletContext = getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		controller = ctx.getBean(Controller.class);
    }
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fieldString = request.getParameter("field");
		String orderString = request.getParameter("order");
		String nbByPageString = request.getParameter("nbByPage");
		String pageString = request.getParameter("page");
		String computerSearch =  request.getParameter("search");

		if(fieldString!=null && !fieldString.equals(""))
			field = fieldString;
		
		if(orderString!=null && !orderString.equals(""))
			order = orderString;
		
		if(nbByPageString!=null)
			nbByPage = Integer.parseInt(nbByPageString);
		if(pageString!=null)
			page = Integer.parseInt(pageString);

		int nbComputerFound = controller.getNumberOfComputer();
		int lastPage = (int) Math.ceil(nbComputerFound/(double)nbByPage) ;
		
		switch (field) {
			case "name":	  sortingField = SortingField.NAME; 				break;
			case "introDate": sortingField = SortingField.DATE_INTRODUCTION;	break;
			case "disconDate":sortingField = SortingField.DATE_DISCONTINUATION;	break;
			case "company":	  sortingField = SortingField.COMPANY; 				break;
			default: 		  sortingField = SortingField.ID; 					break;
		}
		switch (order) {
			case "asc": sortingOrder = SortingOrder.ASC;  break;
			case "desc":sortingOrder = SortingOrder.DESC; break;
			default: 	sortingOrder = SortingOrder.ASC;  break;
		}	
		
		List<ComputerDTO> computers	= new ArrayList<>();
		if(computerSearch!=null) {
			Optional<ComputerDTO> optComputer = controller.getComputerByName(computerSearch);
			if (optComputer.isPresent())
				computers.add(optComputer.get());
		}else {
			computers = controller.getComputerPage(page, nbByPage,sortingField, sortingOrder);
		}
		
		request.setAttribute("nbComputerFound", nbComputerFound);
		request.setAttribute("lastPage",lastPage);
		request.setAttribute("page", page);
		request.setAttribute("computers", computers);
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
