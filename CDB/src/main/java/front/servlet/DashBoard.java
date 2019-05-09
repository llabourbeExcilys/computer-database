package front.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.controller.Controller;
import back.dao.SortingField;
import back.dao.SortingOrder;
import back.dto.ComputerDTO;


@WebServlet(urlPatterns = {"/dashboard"})
public class DashBoard extends HttpServlet {

	private static final long serialVersionUID = 5700829257941123519L;

	private Controller controller = Controller.getInstance();
	
	private int nbByPage = 10;
	private int page = 1;
	private String order = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String orderString = request.getParameter("order");
		String nbByPageString = request.getParameter("nbByPage");
		String pageString = request.getParameter("page");
		String computerSearch =  request.getParameter("search");

		if(orderString!=null)
			order = orderString;
		if(nbByPageString!=null)
			nbByPage = Integer.parseInt(nbByPageString);
		if(pageString!=null)
			page = Integer.parseInt(pageString);

		int nbComputerFound = controller.getNumberOfComputer();
		int lastPage = (int) Math.ceil(nbComputerFound/(double)nbByPage) ;
		
		
		List<ComputerDTO> computers	= new ArrayList<>();
		if(computerSearch!=null) {
			Optional<ComputerDTO> optComputer = controller.getComputerByName(computerSearch);
			if (optComputer.isPresent())
				computers.add(optComputer.get());
		}else {
			if(order == null)
				computers = controller.getComputerPage(page, nbByPage,SortingField.ID, SortingOrder.ASC);
			else if(order.equals("asc"))
				computers = controller.getComputerPage(page, nbByPage,SortingField.NAME, SortingOrder.ASC);
			else if(order.equals("desc"))
				computers = controller.getComputerPage(page, nbByPage,SortingField.NAME, SortingOrder.DESC);
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
