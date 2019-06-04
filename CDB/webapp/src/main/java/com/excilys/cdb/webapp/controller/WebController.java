package com.excilys.cdb.webapp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.mapper.CompanyMapper;
import com.excilys.cdb.binding.mapper.ComputerMapper;
import com.excilys.cdb.core.exception.ComputerDtoNotMatchingException;
import com.excilys.cdb.core.exception.RequestedPageException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.core.model.ComputerBuilder;
import com.excilys.cdb.persistence.dao.SortingField;
import com.excilys.cdb.persistence.dao.SortingOrder;
import com.excilys.cdb.service.Service;

@Component
public class WebController {

	private Service service;
	private final CompanyMapper companyMapper;
	private final ComputerMapper computerMapper;
	
	
	public WebController(Service service, CompanyMapper companyMapper, ComputerMapper computerMapper) {
		super();
		this.service = service;
		this.companyMapper = companyMapper;
		this.computerMapper = computerMapper;
	}

	// Create
	
	public long addComputer(ComputerDTO computerDTO) {
		Company company = null;
		if(computerDTO.getCompanyId() != null) {
			Optional<Company> companyOptional = service.getCompanyById(computerDTO.getCompanyId());
			if(companyOptional.isPresent())
				company=companyOptional.get();
		}
	
		Computer computer = new ComputerBuilder(computerDTO.getId(), computerDTO.getName())
				.withIntroductionDate(computerDTO.getLdIntroduced())
				.withdiscontinuationDate(computerDTO.getLdDiscontinued())
				.withCompany(company)
				.build();
		
		return service.addComputer(computer);
			
	}
	
	// Read

	public List<CompanyDTO> getCompanyList() {
		return service.getCompanyList().stream()
				.map(companyMapper::companyToDto)
				.collect(Collectors.toList());
	}
	
	public List<ComputerDTO> getComputerList() {		
		return service.getComputerList().stream()
				.map(computerMapper::computerToDTO)
				.collect(Collectors.toList());
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
	
	public long getNumberOfComputer() {
		return service.getNumberOfComputer();
	}
	


	// Update
	
	private Computer getComputer(ComputerDTO computerDTO) {
		Optional<Computer> cOptional = service.getComputerById(computerDTO.getId());
		if(!cOptional.isPresent())
			throw new ComputerDtoNotMatchingException("The computer DTO has no match in database");
		return cOptional.get();
	}
	
	public void updateComputer(ComputerDTO computerDTO) {
		Computer computer = getComputer(computerDTO);
		computer.setName(computerDTO.getName());
		computer.setIntroduced(computerDTO.getLdIntroduced());
		computer.setDiscontinued(computerDTO.getLdDiscontinued());

		Company company = null;
		if(computerDTO.getCompanyId() != null) {
			Optional<Company> companyOptional = service.getCompanyById(computerDTO.getCompanyId());
			if(companyOptional.isPresent())
				company=companyOptional.get();
		}
		computer.setCompany(company);

		service.update(computer);
	}

	// Delete

	public void deleteComputerById(String id) {
		long idL = Long.parseLong(id);
		service.deleteComputerById(idL);
	}
	

	// Getter Setter
	public Service getService() {return service;}
	public void setService(Service service) {this.service = service;}


}
