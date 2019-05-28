package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.SortingField;
import com.excilys.cdb.persistence.dao.SortingOrder;

@org.springframework.stereotype.Service
public class Service {
	
	private final CompanyDAO companyDAO;
	private final ComputerDAO computerDAO;
	
    private Service(CompanyDAO companyDAO, ComputerDAO computerDAO){
    	this.companyDAO = companyDAO;
    	this.computerDAO = computerDAO;
    }
     
	// Create
	
	public long addComputer(Computer computer) {
		return computerDAO.addComputer(computer);
	}
	
	// Read
	
	public List<Computer> getComputerList() {
		return computerDAO.getComputerList();
	}
	
	public List<Company> getCompanyList() {
		return companyDAO.getCompanyList();
	}
	
	public int getNumberOfComputer() {
		return computerDAO.getNumberOfComputer();
	}

	public Optional<Computer> getComputerById(long idL) {
		return computerDAO.getComputerById(idL);
	}
	
	public Optional<Computer> getComputerByName(String computerSearch) {
		return computerDAO.getComputerByName(computerSearch);
	}
	
	public Optional<Company> getCompanyById(long idL) {
		return companyDAO.getCompanyById(idL);
	}
		
	public List<Computer> getComputerPage(int page, int nbByPage, SortingField field, SortingOrder order) {
		return computerDAO.getComputerPage(page,nbByPage,field,order);
	}
	
	// Update
	
	public void update(Computer c){
		computerDAO.update(c);
	}
	
	// Delete

	public void deleteComputerById(long id) {
		computerDAO.deleteComputerById(id);
	}

	public void deleteCompanyById(Long id) {
		companyDAO.deleteCompanyById(id);
	}

}
