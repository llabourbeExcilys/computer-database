package back.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import back.dto.CompanyDTO;
import back.dto.ComputerDTO;
import back.exception.BadCompanyIdException;
import back.exception.ComputerDtoNotMatchingException;
import back.exception.RequestedPageException;
import back.mapper.CompanyMapper;
import back.mapper.ComputerMapper;
import back.model.Company;
import back.model.Computer;
import back.service.Service;
import back.validator.ComputerValidator;

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

	public long addComputer(String name, 
							Optional<String> dateIntroduction,
							Optional<String> dateDiscontinued,
							Optional<String> companyID) {
		
		ComputerDTO computerDTO;
		
		computerDTO = ComputerValidator.validate(name,
					dateIntroduction,
					dateDiscontinued,
					companyID);
	
		return service.addComputer(computerDTO);
	}


	// Read

	public List<CompanyDTO> getCompanyList() {
		return service.getCompanyList()
				.stream()
				.map(p -> companyMapper.companyToDto(p))
				.collect(Collectors.toList());
	}
	
	
	public Optional<CompanyDTO> getCompanyById(String id){
		long idL = Long.parseLong(id);
		return getCompanyById(idL);
	}
	
	public Optional<CompanyDTO> getCompanyById(long id){
		Optional<Company> cOptional = service.getCompanyById(id);
		return companyMapper.companyToDto(cOptional);
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
		LocalDate introductionDate = ComputerValidator.checkAndCreateDate(date);		
		
		
		computerDTO.setLdIntroduced(introductionDate);
		
		ComputerValidator.validate(computerDTO);
		
		
		Computer computer = getComputer(computerDTO);
		service.update(computer);
	}

	public void updateComputerDiscontinued(ComputerDTO computerDTO, String date) {
		LocalDate discontDate = ComputerValidator.checkAndCreateDate(date);
		computerDTO.setLdDiscontinued(discontDate);

		ComputerValidator.validate(computerDTO);
	
		Computer computer = getComputer(computerDTO);
		service.update(computer);
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
