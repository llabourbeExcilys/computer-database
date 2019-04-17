package com.excilys.cdb.model;

import java.util.List;

import com.excilys.cdb.model.dao.CompanyDAO;
import com.excilys.cdb.model.dao.ComputerDAO;

public class Service {
	
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	
	
	
	public Service() {
		super();
		companyDAO = new CompanyDAO();
		computerDAO = new ComputerDAO();
	}

	public List<Computer> getComputerList() {
		return computerDAO.getComputerList();
	}
	
	public List<Company> getCompanyList() {
		return companyDAO.getCompanyList();
	}

	public Computer getComputerById(long idL) {
		return computerDAO.getComputerById(idL);
	}

	public void addComputer(String name) {
		computerDAO.addComputer(name);
	}

	public void deleteComputerById(long idL2) {
		computerDAO.deleteComputerById(idL2);
	}

	public void updateName(Computer c, String name) {
		computerDAO.updateName(c,name);
		
	}

	public void updateComputerCompany(Computer c, long company) {
		computerDAO.updateCompany(c,company);
	}
	

}