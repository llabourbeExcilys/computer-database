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
		
		// les dates marchent pas
		String introduced = request.getParameter("introduced").trim();
		String discontinued = request.getParameter("discontinued").trim();
		
		Optional<String> intrOptional = Optional.ofNullable(introduced.equals("") ? null : introduced);
		Optional<String> discOptional = Optional.ofNullable(discontinued.equals("") ? null : discontinued);

		
//		System.out.println("introduced:"+introduced);
//		System.out.println("discontinued:"+discontinued);

		String companyIdString = request.getParameter("companyId");
		
		Optional<ComputerDTO> optComputerDTO = controller.getComputerById(id);
		
		if(optComputerDTO.isPresent()) {
			ComputerDTO computerDTO = optComputerDTO.get();
			controller.updateName(computerDTO, name);
			
			
			if(intrOptional.isPresent() && discOptional.isPresent()) 
				controller.updateComputerIntroDiscon(computerDTO, intrOptional.get(), discOptional.get());
			else if (intrOptional.isPresent())
				controller.updateComputerIntroduced(computerDTO, intrOptional.get());
			else if (discOptional.isPresent())
				controller.updateComputerDiscontinued(computerDTO, discOptional.get());
			
			Optional<CompanyDTO> cOptional = controller.getCompanyById(companyIdString);
			if(cOptional.isPresent())
				controller.updateComputerCompany(computerDTO, companyIdString);
		}
		
		
		getServletContext().getRequestDispatcher("/dashboard").forward(request, response);

	}

}
