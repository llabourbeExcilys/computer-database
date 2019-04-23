package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.dao.CompanyDAO;
import model.dao.ComputerDAO;

public class Service {
	
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	
	public Service(ComputerDAO computerDAO, CompanyDAO companyDAO) {
		super();
		this.companyDAO = companyDAO;
		this.computerDAO = computerDAO;
	}
	
	// Create
	
	public long addComputer(String name, LocalDate ldateIntroduction, LocalDate ldateDiscontinuation, Optional<Long> idL) {
		return computerDAO.addComputer(name,ldateIntroduction,ldateDiscontinuation,idL);
	}
	
	// Read
	
	public List<Computer> getComputerList() {
		return computerDAO.getComputerList();
	}
	
	public List<Company> getCompanyList() {
		return companyDAO.getCompanyList();
	}

	public Optional<Computer> getComputerById(long idL) {
		return computerDAO.getComputerById(idL);
	}
	
	public Optional<Company> getCompanyById(long idL) {
		return companyDAO.getCompanyByID(idL);
	}

	// Update
	
	public void update(Computer c){
		computerDAO.update(c);
	}
	
	// Delete

	public void deleteComputerById(long idL2) {
		computerDAO.deleteComputerById(idL2);
	}

}
