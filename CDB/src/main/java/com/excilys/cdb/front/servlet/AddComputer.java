package com.excilys.cdb.front.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.cdb.back.controller.Controller;
import com.excilys.cdb.back.dto.CompanyDTO;

@WebServlet(urlPatterns = {"/addComputer"})
public class AddComputer extends HttpServlet{

	private static final long serialVersionUID = 2658501332987983872L;

	private static Controller controller;

	@Override
    public void init() throws ServletException {
		super.init();
		controller = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext())
					.getBean(Controller.class);
    }	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CompanyDTO> companies = controller.getCompanyList();
		request.setAttribute("companies", companies);
		getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName");	
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		Optional<String> optIntroduced = Optional.ofNullable(introduced.equals("") ? null : introduced );
		Optional<String> optDiscontinued = Optional.ofNullable(discontinued.equals("") ? null : discontinued );
		Optional<String> optCompanyId = Optional.ofNullable(companyId.equals("-1") ? null : companyId );
	
		controller.addComputer(computerName, optIntroduced, optDiscontinued, optCompanyId);
		
		getServletContext().getRequestDispatcher("/dashboard").forward(request, response);
	}
	
}
