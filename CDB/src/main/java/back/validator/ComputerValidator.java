package back.validator;

import java.time.LocalDate;
import java.util.Optional;

import back.dto.ComputerDTO;
import back.exception.BadCompanyIdException;
import back.exception.BadInputException;
import back.exception.DateFormatException;
import back.model.Company;
import back.service.Service;

public class ComputerValidator {
	
	private Service service;
	
    private ComputerValidator(){
    	this.service = Service.getInstance();
    }
     
    private static ComputerValidator INSTANCE = null;
     
    public static synchronized ComputerValidator getInstance(){
        if (INSTANCE == null)
        	INSTANCE = new ComputerValidator(); 
        return INSTANCE;
    }
	

	public void validate(ComputerDTO computerDTO) {
		validateName(computerDTO.getName());
		validateDateOrder(computerDTO.getLdIntroduced(),computerDTO.getLdDiscontinued());
		validateCompany(computerDTO.getCompanyID(), computerDTO.getCompanyName());
	}
	
	private void validateName(String name) {
		String trimedName = name.trim();		
		if(!trimedName.equals(name))
			throw new BadInputException("Il ne peut y avoir d'espace ou de tabulation au début ou à la fin.");
		if (name.equals(""))
			throw new BadInputException("Le nom ne peut etre vide");
	}
	
	private void validateDateOrder(LocalDate ldIntroduced, LocalDate ldDiscontinued) {
		LocalDate ldIntr = ldIntroduced;
		LocalDate ldDisc = ldDiscontinued;

		if (ldIntr != null && ldDisc != null)
			if (ldDisc.isBefore(ldIntr))
				throw new DateFormatException("Discontinuation cannot be anterior to introduction date");
	}
	
	private void validateCompany(Long id, String name) {
		if (id != null) {
			if (id < 0)
				throw new BadCompanyIdException("L'id ne peut pas être <= 0");
			Optional<Company> cOptional = service.getCompanyById(id);
			if(!cOptional.isPresent())
				throw new BadCompanyIdException("L'id de la company ne correspond à aucune company existante");
			if(!cOptional.get().getName().equals(name))
				throw new BadInputException("Le nom de la company n'a aucune correspondance dans la base.");
		}
	}

}
