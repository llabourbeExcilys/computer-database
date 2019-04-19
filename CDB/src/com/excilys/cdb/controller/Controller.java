package com.excilys.cdb.controller;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Service;

public class Controller {

	private Service service;
	
	public Controller(Service service) {
		super();
		this.service = service;
	}

	// Create
	
	public long addComputer(String name) {
		if(name!=null) {
			return service.addComputer(name);
		}
		return -1;
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
		service.updateName(c,name);
	}

	public void updateComputerCompany(Computer c, String company) {
		long companyId  = Long.parseLong(company);
		service.updateComputerCompany(c,companyId);
	}

	public void updateComputerIntroduced(Computer c, String date) {
		LocalDate ldate = checkAndCreateDate(date);
		service.updateComputerIntroduced(c, ldate);
	}

	public void updateComputerDiscontinued(Computer c, String date) {
		LocalDate discontDate = checkAndCreateDate(date);
		LocalDate introductionDate = c.getLdIntroduced();
		
		if(discontDate.isBefore(introductionDate))
			throw new DateTimeException("Discontinuation cannot be anterior to introduction date");

		service.updateComputerDiscontinued(c, discontDate);
	}
	
	// Check if a string can be used to create a LocalDate
	// Expected string format:yyyy-mm-dd
	// Return the LocalDate if ok
	// Throw DateTimeException exception if not ok
	private LocalDate checkAndCreateDate(String date) {
		String [] datePart = date.split("-");
		
		if(datePart.length!=3) 
			throw new DateTimeException("invalide date format");
		
		String year = datePart[0];				
		String month = datePart[1];
		String day = datePart[2];
		
		if(year.length()!=4 || month.length()!=2 || day.length()!=2)
			throw new DateTimeException("invalide year/month/day length");

		int yearI = Integer.parseInt(year);
		int monthI = Integer.parseInt(month);
		int dayI = Integer.parseInt(day);
		
		return  LocalDate.of(yearI, monthI, dayI);
	}
	
	// Delete

	public void deleteComputerById(String id) {
		long idL = Long.parseLong(id);
		service.deleteComputerById(idL);
	}
	
}
