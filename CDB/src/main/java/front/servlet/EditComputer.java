package front.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.controller.Controller;
import back.dto.CompanyDTO;
import back.dto.ComputerDTO;
import back.model.Computer;

@WebServlet(urlPatterns = { "/editComputer" })
public class EditComputer extends HttpServlet {

	private static final long serialVersionUID = -2982660020993022701L;
	
	private Controller controller = Controller.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idToEdit = request.getParameter("idToEdit");
		
		Optional<ComputerDTO> computerOptional = controller.getComputerById(idToEdit);
		
		ComputerDTO computer;
		
		if(computerOptional.isPresent()) {
			computer = computerOptional.get();
			request.setAttribute("computer", computer);
			request.setAttribute("companies", controller.getCompanyList());
			
			
			getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
		}else {
			getServletContext().getRequestDispatcher("/dashboard").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		
		System.out.println("introduced:"+introduced);
		
		// les dates marchent pas
		
		String discontinued = request.getParameter("discontinued");
		String companyIdString = request.getParameter("companyId");
		
		Optional<ComputerDTO> optComputerDTO = controller.getComputerById(id);
		
		if(optComputerDTO.isPresent()) {
			ComputerDTO computerDTO = optComputerDTO.get();
			controller.updateName(computerDTO, name);
			controller.updateComputerIntroduced(computerDTO, introduced);
			controller.updateComputerDiscontinued(computerDTO, discontinued);
			
			Optional<CompanyDTO> cOptional = controller.getCompanyById(companyIdString);
			if(cOptional.isPresent())
				controller.updateComputerCompany(computerDTO, companyIdString);
		}
		
		
		getServletContext().getRequestDispatcher("/dashboard").forward(request, response);

	}

}
