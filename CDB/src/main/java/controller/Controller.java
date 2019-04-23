package controller;

import java.time.LocalDate;
import java.util.List;

import exception.BadCompanyIdException;
import exception.DateFormatException;
import model.Company;
import model.Computer;
import model.Service;

public class Controller {

	private Service service;

	public Controller(Service service) {
		super();
		this.service = service;
	}

	// Create

	public long addComputer(String name, String dIntroduction, String dDiscontinuation, String company) {
		LocalDate ldateIntroduction = null, ldateDiscontinuation = null;

		if (dIntroduction != null)
			ldateIntroduction = checkAndCreateDate(dIntroduction);

		if (dDiscontinuation != null)
			ldateDiscontinuation = checkAndCreateDate(dDiscontinuation);

		if (company != null)
			checkCompanyIdFormat(company);

		if (name != null) {
			return service.addComputer(name, ldateIntroduction, ldateDiscontinuation, company);
		}

		return -1;
	}

	private void checkCompanyIdFormat(String companyId) {
		long idL = Long.parseLong(companyId);
		if (idL < 0)
			throw new BadCompanyIdException("L'id ne peut pas être <= 0");

	}

	// Read

	public List<Computer> getComputerList() {
		return service.getComputerList();
	}

	public List<Company> getCompanyList() {
		return service.getCompanyList();
	}

	public Computer getComputerById(String id) {
		long idL = Long.parseLong(id);
		return getComputerById(idL);
	}

	public Computer getComputerById(long idL) {
		return service.getComputerById(idL);
	}

	// Update
	

	public void updateName(Computer c, String name) {
		c.setName(name);
		service.update(c);
	}

	public void updateComputerCompany(Computer c, String sCompanyId) {
		long companyId = Long.parseLong(sCompanyId);
		Company company = service.getCompanyById(companyId);
		c.setCompany(company);
		service.update(c);
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
		
		System.out.println(yearI+" "+monthI+" "+dayI);

		return LocalDate.of(yearI, monthI, dayI);
	}

	// Delete

	public void deleteComputerById(String id) {
		long idL = Long.parseLong(id);
		service.deleteComputerById(idL);
	}

}
