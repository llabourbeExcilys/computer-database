package com.excilys.cdb.back.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.back.dao.SortingField;
import com.excilys.cdb.back.dao.SortingOrder;
import com.excilys.cdb.back.dto.CompanyDTO;
import com.excilys.cdb.back.dto.ComputerDTO;
import com.excilys.cdb.back.exception.BadCompanyIdException;
import com.excilys.cdb.back.exception.ComputerDtoNotMatchingException;
import com.excilys.cdb.back.exception.DateFormatException;
import com.excilys.cdb.back.exception.RequestedPageException;
import com.excilys.cdb.back.mapper.CompanyMapper;
import com.excilys.cdb.back.mapper.ComputerMapper;
import com.excilys.cdb.back.model.Company;
import com.excilys.cdb.back.model.Computer;
import com.excilys.cdb.back.model.ComputerBuilder;
import com.excilys.cdb.back.service.Service;
import com.excilys.cdb.back.validator.ComputerValidator;

@Component
public class Controller {

	private Service service;
	private final CompanyMapper companyMapper;
	private final ComputerMapper computerMapper;
	private final ComputerValidator computerValidator;
	
	
	public Controller(Service service, CompanyMapper companyMapper, ComputerMapper computerMapper,
			ComputerValidator computerValidator) {
		super();
		this.service = service;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
		this.computerValidator = computerValidator;
	}

	// Create
	
	public long addComputer(String name, 
							Optional<String> dateIntroduction,
							Optional<String> dateDiscontinued,
							Optional<String> companyID) {
		
		ComputerDTO computerDTO = new ComputerDTO();
		
		//Set name
		computerDTO.setName(name);
		
		//Set date of introduction
		if(dateIntroduction.isPresent()) {
			LocalDate ldIntro = checkAndCreateDate(dateIntroduction.get());
			computerDTO.setLdIntroduced(ldIntro);
		}
		
		//Set date of discontinuation
		if(dateDiscontinued.isPresent()) {
			LocalDate ldDisco = checkAndCreateDate(dateDiscontinued.get());
			computerDTO.setLdDiscontinued(ldDisco);
		}
		
		//Set company id and name
		if(companyID.isPresent()) {
			String companyIdString = companyID.get();
			long idL = Long.parseLong(companyIdString);
			Optional<Company> companyById = service.getCompanyById(idL);
			if(companyById.isPresent()) {
				computerDTO.setCompanyID(idL);
				computerDTO.setCompanyName(companyById.get().getName());
			}
		}

		computerValidator.validate(computerDTO);
		
		//Build computer
		Computer computer = new ComputerBuilder(computerDTO.getId(), computerDTO.getName())
				.withIntroductionDate(computerDTO.getLdIntroduced())
				.withdiscontinuationDate(computerDTO.getLdDiscontinued())
				.build();
		
		Optional<Company> companyOptional = service.getCompanyById(computer.getId());
		if(companyOptional.isPresent())
			computer.setCompany(companyOptional.get());
		
		return service.addComputer(computer);
	}


	// Read

	public List<CompanyDTO> getCompanyList() {
		return service.getCompanyList().stream()
				.map(companyMapper::companyToDto)
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
		return service.getComputerList().stream()
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
	
	public List<ComputerDTO> getComputerPage(int page, int nbByPage, SortingField field, SortingOrder order) {
		if(page<=0)
			throw new RequestedPageException("La page demandé ne peut pas etre negative ou egal a 0.");
		if(nbByPage<0)
			throw new RequestedPageException("Le nombre d'ordinateur par page ne peut etre negatif.");

		List<Computer> computers = service.getComputerPage(page,nbByPage, field, order);
		return computers.stream()
				.map(computerMapper::computerToDTO)
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
		computerDTO.setLdIntroduced(introductionDate);
		computerValidator.validate(computerDTO);
	
		Computer computer = getComputer(computerDTO);
		computer.setLdIntroduced(introductionDate);
		service.update(computer);
	}

	public void updateComputerDiscontinued(ComputerDTO computerDTO, String date) {
		LocalDate discontDate = checkAndCreateDate(date);
		computerDTO.setLdDiscontinued(discontDate);
		computerValidator.validate(computerDTO);
	
		Computer computer = getComputer(computerDTO);
		computer.setLdDiscontinued(discontDate);
		service.update(computer);
	}
	
	public void updateComputerIntroDiscon(ComputerDTO computerDTO, String intro, String discon) {
		LocalDate introductionDate = checkAndCreateDate(intro);
		LocalDate discontDate = checkAndCreateDate(discon);

		computerDTO.setLdIntroduced(introductionDate);
		computerDTO.setLdDiscontinued(discontDate);

		computerValidator.validate(computerDTO);
	
		Computer computer = getComputer(computerDTO);
		computer.setLdIntroduced(introductionDate);
		computer.setLdDiscontinued(discontDate);
		service.update(computer);
	}

	// Delete

	public void deleteComputerById(String id) {
		long idL = Long.parseLong(id);
		service.deleteComputerById(idL);
	}
	
	
	public void deleteCompanyById(String id) {
		long idL = Long.parseLong(id);
		service.deleteCompanyById(idL);
	}
	
	// Check if a string can be used to create a LocalDate
	// Expected string format:yyyy-mm-dd
	// Return the LocalDate or throw DateFormatException
	private LocalDate checkAndCreateDate(String date) {
		String[] datePart = date.split("-");

		if (datePart.length != 3)
			throw new DateFormatException("invalide date format");

		String year = datePart[0];
		String month = datePart[1];
		String day = datePart[2];

		if (year.length() != 4)
			throw new DateFormatException("invalide year length");
		else if (month.length() != 2)
			throw new DateFormatException("invalide month length");
		else if (day.length() != 2)
			throw new DateFormatException("invalide day length");

		int yearI = Integer.parseInt(year);
		int monthI = Integer.parseInt(month);
		int dayI = Integer.parseInt(day);

		return LocalDate.of(yearI, monthI, dayI);
	}

	// Getter Setter
	public Service getService() {return service;}
	public void setService(Service service) {this.service = service;}


}
