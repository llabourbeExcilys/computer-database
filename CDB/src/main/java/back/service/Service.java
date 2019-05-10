package back.service;

import java.util.List;
import java.util.Optional;

import back.dao.CompanyDAO;
import back.dao.ComputerDAO;
import back.dao.SortingField;
import back.dao.SortingOrder;
import back.model.Company;
import back.model.Computer;

public class Service {
	
	private static CompanyDAO companyDAO;
	private static ComputerDAO computerDAO;
	

    private Service(){
    	companyDAO = CompanyDAO.getInstance();
    	computerDAO = ComputerDAO.getInstance();
    }
     
    private static Service INSTANCE = null;
     
    public static synchronized Service getInstance(){
        if (INSTANCE == null)
        	INSTANCE = new Service(); 
        return INSTANCE;
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
