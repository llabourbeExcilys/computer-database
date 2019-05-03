package back.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import back.dto.CompanyDTO;
import back.dto.ComputerDTO;
import back.exception.BadCompanyIdException;
import back.exception.ComputerDtoNotMatchingException;
import back.exception.DateFormatException;
import back.exception.RequestedPageException;
import back.mapper.CompanyMapper;
import back.mapper.ComputerMapper;
import back.model.Company;
import back.model.Computer;
import back.service.Service;

public class Controller {
	
	//private static Logger logger = LoggerFactory.getLogger( Main.class );


	private static Service service;
	private static CompanyMapper companyMapper;
	private static ComputerMapper computerMapper;

	/** Constructeur privé */
    private Controller(){}
     
    /** Instance unique non préinitialisée */
    private static Controller INSTANCE = null;
     
    /** Point d'accès pour l'instance unique du singleton */
    public static synchronized Controller getInstance(){
    	service = Service.getInstance();
    	companyMapper = CompanyMapper.getInstance();
    	computerMapper = ComputerMapper.getInstance();
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

	public List<CompanyDTO> getCompanyList() {
		return service.getCompanyList()
				.stream()
				.map(p -> companyMapper.companyToDto(p))
				.collect(Collectors.toList());
	}
	
	public List<ComputerDTO> getComputerList() {		
		
		return service.getComputerList()
				.stream()
				.map(computerMapper::computerToDTO)
				.collect(Collectors.toList());
	}

	public Optional<ComputerDTO> getComputerById(String id) {
		long idL = Long.parseLong(id);
		return getComputerById(idL);
	}

	public Optional<ComputerDTO> getComputerById(long idL) {
		Optional<Computer> cOptional = service.getComputerById(idL);
		return computerMapper.computerToDTO(cOptional);
	}
	
	public Optional<ComputerDTO> getComputerByName(String computerSearch) {
		Optional<Computer> cOptional = service.getComputerByName(computerSearch);
		return computerMapper.computerToDTO(cOptional);		
	}
	
	public List<ComputerDTO> getComputerPage(int page, int nbByPage){
		if(page<=0)
			throw new RequestedPageException("La page demandé ne peut pas etre negative ou egal a 0.");
		if(nbByPage<0)
			throw new RequestedPageException("Le nombre d'ordinateur par page ne peut etre negatif.");
		
		List<Computer> computers = service.getComputerPage(page,nbByPage);
		return computers.stream()
				.map(p -> computerMapper.computerToDTO(p))
				.collect(Collectors.toList());
	}
	
	public int getNumberOfComputer() {
		return service.getNumberOfComputer();
	}

	// Update
	
	private Computer getComputer(ComputerDTO computerDTO) {
		Optional<Computer> cOptional = service.getComputerById(computerDTO.getId());
		
		if(!cOptional.isPresent())
			throw new ComputerDtoNotMatchingException("The computer DTO has no match in database");
		
		return cOptional.get();
	}
	
	public void updateName(ComputerDTO computerDTO, String name) {
		
		Computer computer = getComputer(computerDTO);
		computer.setName(name);
		service.update(computer);
	}

	public void updateComputerCompany(ComputerDTO computerDTO, String sCompanyId) {
		long companyId = Long.parseLong(sCompanyId);
		Optional<Company> company = service.getCompanyById(companyId);
		
		if(company.isPresent()) {	
			Computer computer = getComputer(computerDTO);
			computer.setCompany(company.get());
			service.update(computer);
		}else {
			throw new BadCompanyIdException("Le nouvel company id ne correspond à aucune company dans la base");
		}			
	}

	public void updateComputerIntroduced(ComputerDTO computerDTO, String date) {
		LocalDate introductionDate = checkAndCreateDate(date);
		LocalDate discontDate = computerDTO.getLdDiscontinued();
		
		if(discontDate != null) 
			if (discontDate.isBefore(introductionDate))
				throw new DateFormatException("Discontinuation cannot be anterior to introduction date");
		
		Computer computer = getComputer(computerDTO);
		computer.setLdIntroduced(introductionDate);
		service.update(computer);
	}

	public void updateComputerDiscontinued(ComputerDTO computerDTO, String date) {
		LocalDate introductionDate = computerDTO.getLdIntroduced();
		LocalDate discontDate = checkAndCreateDate(date);

		if(introductionDate != null) 
			if (discontDate.isBefore(introductionDate))
				throw new DateFormatException("Discontinuation cannot be anterior to introduction date");
	
		Computer computer = getComputer(computerDTO);
		computer.setLdDiscontinued(discontDate);
		service.update(computer);
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

	// Getter Setter
	public static Service getService() {return service;}
	public static void setService(Service service) {Controller.service = service;}

}
