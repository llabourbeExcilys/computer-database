package com.excilys.cdb.console.validator;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.core.exception.BadCompanyIdException;
import com.excilys.cdb.core.exception.BadInputException;
import com.excilys.cdb.core.exception.DateFormatException;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.service.service.Service;

@Component
public class ComputerValidator {
	
	private final Service service;
	
    private ComputerValidator(Service service){
    	this.service = service;
    }
	
	public void validate(ComputerDTO computerDTO) {
		validateName(computerDTO.getName());
		validateDateOrder(computerDTO.getLdIntroduced(),computerDTO.getLdDiscontinued());
		validateCompany(computerDTO.getCompanyId(), computerDTO.getCompanyName());
	}
	
	private void validateName(String name) {
		String trimedName = name.trim();		
		if(!trimedName.equals(name))
			throw new BadInputException("Le nom ne peut débuter ou terminer par un espace ou une tabulation.");
		if (name.equals(""))
			throw new BadInputException("Le nom ne peut être vide");
	}
	
	private void validateDateOrder(LocalDate ldIntr, LocalDate ldDisc) {
		if (ldIntr != null && ldDisc != null && ldDisc.isBefore(ldIntr))
			throw new DateFormatException("La date d'arrêt ne peut être antérieur à la date d'introduction.");
	}
	
	private void validateCompany(Long id, String name) {
		if (id != null) {
			if (id < 0)
				throw new BadCompanyIdException("L'id ne peut pas être <= 0");
			Optional<Company> cOptional = service.getCompanyById(id);
			if(!cOptional.isPresent())
				throw new BadCompanyIdException("L'id de la company ne correspond à aucune company existante.");
		}
	}

}
