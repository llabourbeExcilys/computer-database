package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import model.dao.CompanyDAO;
import model.dao.ComputerDAO;

public class Service {
	
	private static CompanyDAO companyDAO;
	private static ComputerDAO computerDAO;
	

	
	
	/** Constructeur privé */
    private Service(){}
     
    /** Instance unique non préinitialisée */
    private static Service INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Service getInstance(){
    	companyDAO = CompanyDAO.getInstance();
    	computerDAO = ComputerDAO.getInstance();
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

	// Update
	
	public void update(Computer c){
		computerDAO.update(c);
	}
	
	// Delete

	public void deleteComputerById(long idL2) {
		computerDAO.deleteComputerById(idL2);
	}

}
