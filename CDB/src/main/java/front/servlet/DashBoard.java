package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.Computer;


@WebServlet(urlPatterns = {"/dashboard"})
public class DashBoard extends HttpServlet {

	private static final long serialVersionUID = 5700829257941123519L;

	private Controller controller = Controller.getInstance();
	
	private int nbByPage = 10;
	private int page = 1;
	
	
	private static Logger logger = LoggerFactory.getLogger( DashBoard.class );
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nbByPageString = request.getParameter("nbByPage");
		if(nbByPageString!=null)
			nbByPage = Integer.parseInt(nbByPageString);
		
		String pageString = request.getParameter("page");
		if(pageString!=null)
			page = Integer.parseInt(pageString);

		int nbComputerFound = controller.getNumberOfComputer();
		int lastPage = nbComputerFound/nbByPage;
		
		
		List<Computer> computers = new ArrayList<>();
		String computerSearch =  request.getParameter("search");
		if(computerSearch!=null) {
			Optional<Computer> optComputer = controller.getComputerByName(computerSearch);
			if (optComputer.isPresent()) 
				computers.add(optComputer.get());
		}else {
			computers = controller.getComputerPage(page, nbByPage);
		}

		
		request.setAttribute("nbComputerFound", nbComputerFound);
		request.setAttribute("lastPage",lastPage);
		request.setAttribute("page", page);
		request.setAttribute("computers", computers);
		
		getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
