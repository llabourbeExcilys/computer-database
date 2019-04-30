package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import exception.BadCompanyIdException;
import exception.DateFormatException;
import exception.RequestedPageException;
import model.Company;
import model.Computer;
import model.Service;

public class Controller {
	
	//private static Logger logger = LoggerFactory.getLogger( Main.class );


	private static Service service;

	/** Constructeur privé */
    private Controller(){}
     
    /** Instance unique non préinitialisée */
    private static Controller INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Controller getInstance(){
    	service = Service.getInstance();
        if (INSTANCE == null)
        	INSTANCE = new Controller(); 
        
        return INSTANCE;
    }

	// Create

	public long addComputer(String name, Optional<String> dateIntroduction, Optional<String> dateDiscontinued, Optional<String> companyID) {
		LocalDate ldateIntroduction = null, ldateDiscontinuation = null;
		Optional<Long> idL = Optional.empty();
		
		
		if (dateIntroduction.isPresent())
			ldateIntroduction = checkAndCreateDate(dateIntroduction.get());
		if (dateDiscontinued.isPresent())
			ldateDiscontinuation = checkAndCreateDate(dateDiscontinued.get());
		if (companyID.isPresent())
			idL = Optional.ofNullable(checkCompanyIdFormat(companyID.get()));
		return service.addComputer(name, ldateIntroduction, ldateDiscontinuation, idL);
	}

	private long checkCompanyIdFormat(String companyId) {
		long idL = Long.parseLong(companyId);
		if (idL < 0)
			throw new BadCompanyIdException("L'id ne peut pas être <= 0");
		return idL;
	}

	// Read

	public List<Company> getCompanyList() {
		return service.getCompanyList();
	}
	
	public List<Computer> getComputerList() {
		return service.getComputerList();
	}

	public Optional<Computer> getComputerById(String id) {
		long idL = Long.parseLong(id);
		return getComputerById(idL);
	}

	public Optional<Computer> getComputerById(long idL) {
		return service.getComputerById(idL);
	}
	
	public List<Computer> getComputerPage(int page, int nbByPage){
		if(page<=0)
			throw new RequestedPageException("La page demandé ne peut pas etre negative ou egal a 0.");
		if(nbByPage<0)
			throw new RequestedPageException("Le nombre d'ordinateur par page ne peut etre negatif.");
		
		return service.getComputerPage(page,nbByPage);
	}
	
	public int getNumberOfComputer() {
		return service.getNumberOfComputer();
	}

	// Update
	
	public void updateName(Computer c, String name) {
		c.setName(name);
		service.update(c);
	}

	public void updateComputerCompany(Computer c, String sCompanyId) {
		long companyId = Long.parseLong(sCompanyId);
		Optional<Company> company = service.getCompanyById(companyId);
		if(company.isPresent()) {
			c.setCompany(company.get());
			service.update(c);
		}else {
			throw new BadCompanyIdException("Le nouvel company id ne correspond à aucune company dans la base");
		}			
	}

	public void updateComputerIntroduced(Computer c, String date) {
		LocalDate introductionDate = checkAndCreateDate(date);
		LocalDate discontDate = c.getLdDiscontinued();
		
		if(discontDate != null) {
			if (discontDate.isBefore(introductionDate))
				throw new DateFormatException("Discontinuation cannot be anterior to introduction date");
		}
		
		c.setLdIntroduced(introductionDate);
		service.update(c);
	}

	public void updateComputerDiscontinued(Computer c, String date) {
		LocalDate introductionDate = c.getLdIntroduced();
		LocalDate discontDate = checkAndCreateDate(date);

		if(introductionDate != null) {
			if (discontDate.isBefore(introductionDate))
				throw new DateFormatException("Discontinuation cannot be anterior to introduction date");
		}
	
		c.setLdDiscontinued(discontDate);
		service.update(c);
	}

	// Check if a string can be used to create a LocalDate
	// Expected string format:yyyy-mm-dd
	// Return the LocalDate if ok
	// Throw DateFormatException exception if not ok
	private LocalDate checkAndCreateDate(String date) {
		String[] datePart = date.split("-");

		if (datePart.length != 3)
			throw new DateFormatException("invalide date format");

		String year = datePart[0];
		String month = datePart[1];
		String day = datePart[2];

		
		if (year.length() != 4 ) 
			throw new DateFormatException("invalide year length");
		else if(month.length() != 2) 
			throw new DateFormatException("invalide month length");
		else if(day.length() != 2) 
			throw new DateFormatException("invalide day length");
	
		
		int yearI = Integer.parseInt(year);
		int monthI = Integer.parseInt(month);
		int dayI = Integer.parseInt(day);
		
		//logger.debug(yearI+" "+monthI+" "+dayI);

		return LocalDate.of(yearI, monthI, dayI);
	}

	// Delete

	public void deleteComputerById(String id) {
		long idL = Long.parseLong(id);
		service.deleteComputerById(idL);
	}

	public static Service getService() {
		return service;
	}

	public static void setService(Service service) {
		Controller.service = service;
	}
	
	

}
