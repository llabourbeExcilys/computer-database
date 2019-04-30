package view;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.Computer;


@WebServlet( name = "HelloWorldServlet",
description = "Testing servlet",
urlPatterns = {"/dashboard"})
public class DashBoard extends HttpServlet {

	private static final long serialVersionUID = 5700829257941123519L;

	private Controller controller = Controller.getInstance();
	
	private int nbByPage = 10;
	private int page = 1;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		String nbByPageString = request.getParameter("nbByPage");
		if(nbByPageString!=null)
			nbByPage = Integer.parseInt(nbByPageString);
		
		String pageString = request.getParameter("page");
		if(pageString!=null)
			page = Integer.parseInt(pageString);

		List<Computer> computers = controller.getComputerPage(page, nbByPage);

		request.setAttribute("computers", computers);
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
