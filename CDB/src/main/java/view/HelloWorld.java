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
urlPatterns = {"/"})
public class HelloWorld extends HttpServlet {

	private static final long serialVersionUID = 5700829257941123519L;

	private Controller controller = Controller.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Computer> computers = controller.getComputerPage(1, 10);

		request.setAttribute("computers", computers);
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
