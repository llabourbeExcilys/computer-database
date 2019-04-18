package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.List;

import com.excilys.cdb.model.dao.CompanyDAO;
import com.excilys.cdb.model.dao.ComputerDAO;

public class Service {
	
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	
	public Service(ComputerDAO computerDAO, CompanyDAO companyDAO) {
		super();
		this.companyDAO = companyDAO;
		this.computerDAO = computerDAO;
	}
	
	// Create
 
	public long addComputer(String name) {
		return computerDAO.addComputer(name);
	}
	
	// Read
	
	public List<Computer> getComputerList() {
		return computerDAO.getComputerList();
	}
	
	public List<Company> getCompanyList() {
		return companyDAO.getCompanyList();
	}

	public Computer getComputerById(long idL) {
		return computerDAO.getComputerById(idL);
	}

	// Update
	
	public void updateName(Computer c, String name) {
		computerDAO.updateName(c,name);
	}

	public void updateComputerCompany(Computer c, long company) {
		computerDAO.updateCompany(c,company);
	}

	public void updateComputerIntroduced(Computer c, LocalDate ldate) {
		computerDAO.updateComputerIntroduced(c,ldate);
	}

	public void updateComputerDiscontinued(Computer c, LocalDate ldate) {
		computerDAO.updateComputerDiscontinued(c,ldate);
	}
	
	// Delete

	public void deleteComputerById(long idL2) {
		computerDAO.deleteComputerById(idL2);
	}

}
