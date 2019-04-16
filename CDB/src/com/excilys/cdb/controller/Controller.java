package com.excilys.cdb.controller;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Service;

public class Controller {

	private Service service;
	
	
	
	public Controller() {
		super();
		service = new Service();
	}

	public List<Computer> getComputerList() {
		return service.getComputerList();
	}
	
	public List<Company> getCompanyList() {
		return service.getCompanyList();
	}

	public Computer getComputerById(String id) {

		long idL = Long.parseLong(id);
		
		return service.getComputerById(idL);
	}
	
	
	
}
