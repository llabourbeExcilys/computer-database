package back.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import back.dao.CompanyDAO;
import back.dao.ComputerDAO;
import back.model.Company;
import back.model.Computer;

public class Service {
	
	private static CompanyDAO companyDAO;
	private static ComputerDAO computerDAO;
	

	
	
	/** Constructeur privé */
    private Service(){
    	companyDAO = CompanyDAO.getInstance();
    	computerDAO = ComputerDAO.getInstance();
    }
     
    /** Instance unique non préinitialisée */
    private static Service INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Service getInstance(){
        if (INSTANCE == null)
        	INSTANCE = new Service(); 
        return INSTANCE;
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
	
	public List<Computer> getComputerPage(int page, int nbByPage) {
		return computerDAO.getComputerPage(page,nbByPage);
	}

	// Update
	
	public void update(Computer c){
		computerDAO.update(c);
	}
	
	// Delete

	public void deleteComputerById(long idL2) {
		computerDAO.deleteComputerById(idL2);
	}

	public int getNumberOfComputer() {
		return computerDAO.getNumberOfComputer();
	}

	public Optional<Computer> getComputerByName(String computerSearch) {
		return computerDAO.getComputerByName(computerSearch);
	}

	

}
