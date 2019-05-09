package back.validator;

import java.time.LocalDate;
import java.util.Optional;
import back.dto.ComputerDTO;
import back.exception.BadCompanyIdException;
import back.exception.BadInputException;
import back.exception.DateFormatException;

public class ComputerValidator {

	public static ComputerDTO validate(String name, Optional<String> dateIntroduction,
			Optional<String> dateDiscontinued, Optional<String> companyID) {

		name = name.trim();
		if (name.equals(""))
			throw new BadInputException("Le nom ne peut etre vide");
		
		ComputerDTO computerDTO = new ComputerDTO();
		computerDTO.setName(name);

		if (dateIntroduction.isPresent())
			computerDTO.setLdIntroduced(checkAndCreateDate(dateIntroduction.get()));
		if (dateDiscontinued.isPresent())
			computerDTO.setLdDiscontinued(checkAndCreateDate(dateDiscontinued.get()));
		if (companyID.isPresent())
			computerDTO.setCompanyID(checkCompanyIdFormat(companyID.get()));

		checkDateOrder(computerDTO);
		return computerDTO;
	}

	public static void validate(ComputerDTO computerDTO) {
		checkDateOrder(computerDTO);
		if (computerDTO.getCompanyID() != null)
			checkCompanyIdFormat(computerDTO.getCompanyID());
	}

	// Check if a string can be used to create a LocalDate
	// Expected string format:yyyy-mm-dd
	// Return the LocalDate if ok
	// Throw DateFormatException exception if not ok
	public static LocalDate checkAndCreateDate(String date) {
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

	private static void checkDateOrder(ComputerDTO computerDTO) {
		LocalDate ldIntr = computerDTO.getLdIntroduced();
		LocalDate ldDisc = computerDTO.getLdDiscontinued();

		if (ldIntr != null && ldDisc != null)
			if (ldDisc.isBefore(ldIntr))
				throw new DateFormatException("Discontinuation cannot be anterior to introduction date");
	}

	private static long checkCompanyIdFormat(String companyId) {
		return checkCompanyIdFormat(Long.parseLong(companyId));
	}

	private static long checkCompanyIdFormat(long idL) {
		if (idL < 0)
			throw new BadCompanyIdException("L'id ne peut pas être <= 0");
		return idL;
	}

}
