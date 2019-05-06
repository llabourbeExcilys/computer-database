package front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.controller.Controller;

@WebServlet(urlPatterns = {"/deleteComputer"})
public class DeleteComputer extends HttpServlet{

	private static final long serialVersionUID = -3829110288355271044L;

	private Controller controller = Controller.getInstance();

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String computerId = request.getParameter("selection");	
		
		controller.deleteComputerById(computerId);
		
		getServletContext().getRequestDispatcher("/dashboard").forward(request, response);

		
	}
	
}
