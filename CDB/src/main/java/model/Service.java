package model;

import java.time.LocalDate;
import java.util.List;

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
	
	public long addComputer(String name, LocalDate ldateIntroduction, LocalDate ldateDiscontinuation, String companyId) {
		return computerDAO.addComputer(name,ldateIntroduction,ldateDiscontinuation,companyId);
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
	
	public Company getCompanyById(long idL) {
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
